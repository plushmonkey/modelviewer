package com.plushnode.modelviewer.fbx.io;

import com.plushnode.modelviewer.fbx.FBXDocument;
import com.plushnode.modelviewer.fbx.FBXNode;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class FBXBinaryReader {
    private FileChannel channel;
    private ByteBuffer buffer;

    public FBXBinaryReader(RandomAccessFile file) throws IOException {
        this.channel = file.getChannel();
        this.buffer = ByteBuffer.allocate((int)file.length());
        this.buffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public FBXDocument read() throws IOException {
        this.channel.read(this.buffer);
        this.buffer.flip();

        try {
            FBXDocument document = new FBXDocument(readVersion());

            FBXNode node = null;
            do {
                node = readNode();
                if (node != null)
                    document.addNode(node);
            } while (node != null);

            return document;
        } catch (BufferUnderflowException e) {
            throw new IOException("Unexpected end of file");
        }
    }

    private int readVersion() throws IOException {
        byte[] idBytes = new byte[21];

        read(idBytes);

        String id = new String(idBytes, "US-ASCII");
        if (id.compareTo("Kaydara FBX Binary  \0") != 0)
            throw new IOException("Invalid FBX header identifier");

        byte[] unknown = new byte[2];
        read(unknown);
        if (unknown[0] != 0x1A || unknown[1] != 0x0)
            throw new IOException("Invalid FBX header (bytes 21-22 incorrect)");

        return buffer.getInt();
    }

    private FBXNode readNode() throws IOException {
        int endOffset = buffer.getInt();
        int numProperties = buffer.getInt();
        int propertyListLen = buffer.getInt();
        int nameLen = buffer.get();

        byte[] nameBytes = new byte[nameLen];
        read(nameBytes);
        String name = new String(nameBytes, "US-ASCII");

        if (endOffset == 0)
            return null;

        FBXNode node = new FBXNode(name);

        for (int i = 0; i < numProperties; ++i) {
            node.addProperty(readProperty());
        }

        if (endOffset > buffer.position()) {
            FBXNode nestedNode = null;

            do {
                nestedNode = readNode();
                if (nestedNode != null)
                    node.addNode(nestedNode);
            } while (nestedNode != null);
        }

        return node;
    }

    private Object readProperty() throws IOException {
        byte typeCode = buffer.get();
        char c = (char)(typeCode & 0xFF);
        switch (c) {
            case 'Y':
                return buffer.getShort();
            case 'C':
                return buffer.get() != 1;
            case 'I':
                return buffer.getInt();
            case 'F':
                return buffer.getFloat();
            case 'D':
                return buffer.getDouble();
            case 'L':
                return buffer.getLong();

            case 'f':
                return readArray(Float.class, Float.BYTES, ByteBuffer::getFloat);
            case 'd': {
                List<Object> arr = readArray(Double.class, Double.BYTES, ByteBuffer::getDouble);
                return arr;
            }
            case 'l':
                return readArray(Long.class, Long.BYTES, ByteBuffer::getLong);
            case 'i':
                return readArray(Integer.class, Integer.BYTES, ByteBuffer::getInt);
            case 'b':
                return readArray(Byte.class, 1, ByteBuffer::get);

            case 'S':
            case 'R':
            {
                int length = buffer.getInt();
                byte[] bytes = new byte[length];
                read(bytes);
                return bytes;
            }
        }

        return null;
    }

    // todo: maybe find safer way to do this using primitiveClass
    private List<Object> readArray(Class<?> primitiveClass, int size, PrimitiveReader<?> reader) throws IOException {
        int length = buffer.getInt();
        int encoding = buffer.getInt();
        int compressedLength = buffer.getInt();

        byte[] arrayBytes = new byte[length * size];

        if (encoding == 0) {
            read(arrayBytes);
        } else {
            Inflater decompressor = new Inflater();
            byte[] compressedBytes = new byte[compressedLength];
            read(compressedBytes);

            decompressor.setInput(compressedBytes, 0, compressedLength);
            try {
                int inflatedSize = decompressor.inflate(arrayBytes);
                decompressor.end();

                if (inflatedSize != arrayBytes.length)
                    throw new IOException("Error with inflated data size");
            } catch (DataFormatException e) {
                throw new IOException(e.getMessage());
            }
        }

        ByteBuffer bb = ByteBuffer.wrap(arrayBytes);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        List<Object> arr = new ArrayList<>();

        for (int i = 0; i < length; ++i) {
            arr.add(reader.read(bb));
        }

        return arr;
    }

    private void read(byte[] into) throws BufferUnderflowException {
        for (int i = 0; i < into.length; ++i)
            into[i] = buffer.get();
    }

    private interface PrimitiveReader<T> {
        T read(ByteBuffer bb);
    }
}
