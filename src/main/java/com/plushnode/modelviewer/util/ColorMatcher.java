package com.plushnode.modelviewer.util;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.HashMap;
import java.util.Map;

public class ColorMatcher {
    private static ColorMatcher instance = null;
    private Map<Vector3D, Type> types = new HashMap<>();
    private Type defaultType = new Type(1, (byte)0);

    private ColorMatcher() {
        types.put(new Vector3D(0.45490196078431372, 0.45490196078431372, 0.45490196078431372), new Type(1, (byte)0)); // Stone
        types.put(new Vector3D(0.64131994261119085, 0.44184600669536106, 0.36747967479674798), new Type(1, (byte)1)); // Granite
        types.put(new Vector3D(0.62264279624893437, 0.42605285592497871, 0.35345268542199493), new Type(1, (byte)2)); // Polished Granite
        types.put(new Vector3D(0.60609432962374143, 0.60609432962374143, 0.61886592474827773), new Type(1, (byte)3)); // Diorite
        types.put(new Vector3D(0.80475490196078436, 0.80475490196078436, 0.8175980392156863), new Type(1, (byte)4)); // Polished Diorite
        types.put(new Vector3D(0.41296732026143795, 0.41328104575163399, 0.4169411764705882), new Type(1, (byte)5)); // Andesite
        types.put(new Vector3D(0.51139448786507613, 0.51216234745646516, 0.52156862745098043), new Type(1, (byte)6)); // Polished Andesite

        //types.put(new Vector3D(0.58795518207282904, 0.58795518207282904, 0.58795518207282904), new Type(2, (byte)0)); // Grass

        types.put(new Vector3D(0.4743282498184459, 0.33344226579520697, 0.22781408859840233), new Type(3, (byte)0)); // Dirt
        //types.put(new Vector3D(0.46421073479897007, 0.33345216874628641, 0.233630421865716), new Type(3, (byte)1)); // Coarse Dirt
        //types.put(new Vector3D(0.2826220684352172, 0.20319108035371011, 0.10830449826989619), new Type(3, (byte)2)); // Podzol

        types.put(new Vector3D(0.49936481634907481, 0.49936481634907481, 0.49936481634907481), new Type(4, (byte)0)); // Cobblestone

        types.put(new Vector3D(0.73725490196078436, 0.59607843137254901, 0.3843137254901961), new Type(5, (byte)0)); // OakPlank
        types.put(new Vector3D(0.50196078431372548, 0.36862745098039218, 0.21176470588235294), new Type(5, (byte)1)); // SprucePlank
        types.put(new Vector3D(0.84313725490196079, 0.78266253869969038, 0.54220846233230136), new Type(5, (byte)2)); // BirchPlank
        types.put(new Vector3D(0.72156862745098038, 0.52941176470588236, 0.39215686274509803), new Type(5, (byte)3)); // JunglePlank
        types.put(new Vector3D(0.72941176470588232, 0.40113519091847261, 0.2260061919504644), new Type(5, (byte)4)); // AcaciaPlank
        types.put(new Vector3D(0.28627450980392155, 0.18431372549019609, 0.090196078431372548), new Type(5, (byte)5)); // DarkOakPlank

        types.put(new Vector3D(0.20000000000000001, 0.20000000000000001, 0.20000000000000001), new Type(7, (byte)0)); // Bedrock

        //types.put(new Vector3D(0.86487275761368376, 0.83425114726741767, 0.6299123904881101), new Type(12, (byte)0)); // Sand
        //types.put(new Vector3D(0.64143633356957241, 0.32808882589180249, 0.11802504134183794), new Type(12, (byte)1)); // RedSand

        //types.put(new Vector3D(0.50544662309368193, 0.49774872912127816, 0.49750665698378121), new Type(13, (byte)0)); // Gravel
        types.put(new Vector3D(0.52426872388299572, 0.52426872388299572, 0.52426872388299572), new Type(14, (byte)0)); // GoldOre
        types.put(new Vector3D(0.45490196078431372, 0.45490196078431372, 0.45490196078431372), new Type(15, (byte)0)); // IronOre
        types.put(new Vector3D(0.44810996563573885, 0.44810996563573885, 0.44810996563573885), new Type(16, (byte)0)); // CoalOre

        types.put(new Vector3D(0.40862745098039216, 0.3268627450980392, 0.19886274509803922), new Type(17, (byte)0)); // OakWood
        types.put(new Vector3D(0.078813964610234336, 0.036633189861310381, 0.014155906264945003), new Type(17, (byte)1)); // SpruceWood
        types.put(new Vector3D(0.91787969425058158, 0.91811232967763379, 0.8910933865071452), new Type(17, (byte)2)); // BirchWood
        types.put(new Vector3D(0.23484735666418466, 0.18346984363365601, 0.074708364358401586), new Type(17, (byte)3)); // JungleWood

        //types.put(new Vector3D(0.63941176470588235, 0.63878431372549016, 0.6366274509803922), new Type(18, (byte)3)); // JungleLeaves

        types.put(new Vector3D(0.77996589940323957, 0.78303495311167937, 0.3495737425404945), new Type(19, (byte)0)); // Sponge
        types.put(new Vector3D(0.61568627450980395, 0.60009119927040577, 0.19913360693114454), new Type(19, (byte)1)); // SpongeWet

        //types.put(new Vector3D(0.0, 0.0, 0.0), new Type(20, (byte)0)); // Glass

        //types.put(new Vector3D(0.44817927170868349, 0.44817927170868349, 0.44817927170868349), new Type(21, (byte)0)); // LapisOre
        types.put(new Vector3D(0.15065847234416155, 0.26286215978928884, 0.53064091308165051), new Type(22, (byte)0)); // LapisBlock

        types.put(new Vector3D(0.92336134453781515, 0.89098039215686275, 0.72089635854341738), new Type(24, (byte)0)); // Sandstone
        types.put(new Vector3D(0.84925872788139645, 0.81802965088474411, 0.62329029172644668), new Type(24, (byte)1)); // ChiseledSandstone
        types.put(new Vector3D(0.86808278867102395, 0.83447712418300646, 0.6495642701525054), new Type(24, (byte)2)); // SmoothSandstone

        //types.put(new Vector3D(0.57808535178777387, 0.37151095732410611, 0.27035755478662049), new Type(25, (byte)0)); // NoteBlock

        types.put(new Vector3D(0.82908496732026138, 0.82908496732026138, 0.82908496732026138), new Type(35, (byte)0)); // WhiteWool
        types.put(new Vector3D(0.86588235294117655, 0.51423298731257205, 0.28023068050749711), new Type(35, (byte)1)); // OrangeWool
        types.put(new Vector3D(0.68881199538638993, 0.27118800461361015, 0.72535178777393317), new Type(35, (byte)2)); // MagentaWool
        types.put(new Vector3D(0.44857142857142857, 0.56487394957983195, 0.80084033613445382), new Type(35, (byte)3)); // LightBlueWool
        types.put(new Vector3D(0.65082559339525281, 0.609391124871001, 0.14019607843137255), new Type(35, (byte)4)); // YellowWool
        types.put(new Vector3D(0.24147058823529413, 0.64931372549019606, 0.20813725490196081), new Type(35, (byte)5)); // LimeWool
        types.put(new Vector3D(0.80076252723311547, 0.47625272331154683, 0.56497821350762534), new Type(35, (byte)6)); // PinkWool
        types.put(new Vector3D(0.23766915216790943, 0.23766915216790943, 0.23766915216790943), new Type(35, (byte)7)); // GrayWool
        types.put(new Vector3D(0.62834733893557426, 0.65187675070028006, 0.65187675070028006), new Type(35, (byte)8)); // LightGrayWool
        types.put(new Vector3D(0.18862745098039216, 0.4486274509803922, 0.55613445378151261), new Type(35, (byte)9)); // CyanWool
        types.put(new Vector3D(0.51204481792717094, 0.24330532212885156, 0.7415686274509804), new Type(35, (byte)10)); // PurpleWool
        types.put(new Vector3D(0.1741830065359477, 0.21590413943355122, 0.53594771241830064), new Type(35, (byte)11)); // BlueWool
        types.put(new Vector3D(0.29401960784313724, 0.18593137254901962, 0.11490196078431372), new Type(35, (byte)12)); // BrownWool
        types.put(new Vector3D(0.21492233256939139, 0.28724216959511079, 0.10970206264323912), new Type(35, (byte)13)); // GreenWool
        types.put(new Vector3D(0.61315233785822021, 0.21242835595776774, 0.19770739064856713), new Type(35, (byte)14)); // RedWool
        types.put(new Vector3D(0.10478240076518412, 0.090626494500239116, 0.090626494500239116), new Type(35, (byte)15)); // BlackWool

        types.put(new Vector3D(0.99512605042016811, 0.97641456582633057, 0.34011204481792717), new Type(41, (byte)0)); // GoldBlock
        types.put(new Vector3D(0.90269378779549203, 0.90269378779549203, 0.90269378779549203), new Type(42, (byte)0)); // IronBlock

        //types.put(new Vector3D(0.63597025016903319, 0.63597025016903319, 0.63597025016903319), new Type(43, (byte)0)); // DoubleStoneSlab

        types.put(new Vector3D(0.47323529411764703, 0.26294117647058823, 0.20568627450980392), new Type(45, (byte)0)); // Brick

        types.put(new Vector3D(0.2242467718794835, 0.3573888091822095, 0.2242467718794835), new Type(48, (byte)0)); // MossStone
        types.put(new Vector3D(0.063211026985051447, 0.06309454474859251, 0.10021355076684139), new Type(49, (byte)0)); // Obsidian

        //types.put(new Vector3D(0.47478991596638653, 0.47478991596638653, 0.47478991596638653), new Type(56, (byte)0)); // DiamondOre

        types.put(new Vector3D(0.51625386996904021, 0.89618163054695554, 0.88049535603715168), new Type(57, (byte)0)); // DiamondBlock

        //types.put(new Vector3D(0.12902513117923226, 0.10378348522507595, 0.060425296879315103), new Type(58, (byte)0)); // CraftingTable

        //types.put(new Vector3D(0.3880099166103223, 0.24719405003380662, 0.14162722560288482), new Type(60, (byte)0)); // FarmLand

        //types.put(new Vector3D(0.44810996563573885, 0.44810996563573885, 0.44810996563573885), new Type(73, (byte)0)); // RedstoneOre

        types.put(new Vector3D(0.47143312609596688, 0.66730432010202456, 1.0), new Type(79, (byte)0)); // Ice
        types.put(new Vector3D(0.93333333333333335, 1.0, 1.0), new Type(80, (byte)0)); // SnowBlock

        types.put(new Vector3D(0.60902433262461608, 0.63283723127805336, 0.67649421214268846), new Type(82, (byte)0)); // ClayBlock

        //types.put(new Vector3D(0.62745098039215685, 0.35294117647058826, 0.043137254901960784), new Type(86, (byte)0)); // Pumpkin

        types.put(new Vector3D(0.37597348798674396, 0.19850869925434961, 0.19784589892294946), new Type(87, (byte)0)); // Netherrack
        types.put(new Vector3D(0.40209508460918614, 0.31485361267794792, 0.26016653236637122), new Type(88, (byte)0)); // SoulSand
        //types.put(new Vector3D(0.44705882352941179, 0.43529411764705883, 0.28627450980392155), new Type(89, (byte)0)); // Glowstone

        types.put(new Vector3D(0.5628011204481792, 0.42162464985994402, 0.32750700280112049), new Type(99, (byte)0)); // BrownMushroom
        types.put(new Vector3D(0.70983297022512715, 0.11375453885257807, 0.10591140159767611), new Type(100, (byte)0)); // RedMushroom

        types.put(new Vector3D(0.7070223438212494, 0.70337437300501593, 0.15740994072047423), new Type(103, (byte)0)); // Melon

        //types.put(new Vector3D(0.41943119590178413, 0.38014485073308601, 0.40332096802685036), new Type(110, (byte)0)); // mycelium
        types.put(new Vector3D(0.18992458521870287, 0.094358974358974362, 0.11046757164404224), new Type(112, (byte)0)); // NetherBrick
        types.put(new Vector3D(0.85509803921568628, 0.8728104575163399, 0.62653594771241838), new Type(121, (byte)0)); // EndStone

        types.put(new Vector3D(0.49431096382214856, 0.49646506489919912, 0.49513946423639882), new Type(129, (byte)0)); // EmeraldOre
        types.put(new Vector3D(0.3871936274509804, 0.91158088235294121, 0.53541666666666665), new Type(133, (byte)0)); // EmeraldBlock

        types.put(new Vector3D(0.16862745098039217, 0.77459505541346974, 0.73384484228474001), new Type(138, (byte)0)); // Beacon

        types.put(new Vector3D(0.47722473604826549, 0.063499245852187039, 0.02393162393162393), new Type(152, (byte)0)); // RedstoneBlock

        types.put(new Vector3D(0.94125990821860661, 0.93299958281184814, 0.90917813934084268), new Type(155, (byte)0)); // QuartzBlock

        types.put(new Vector3D(0.82156862745098036, 0.6942088463292293, 0.63096215230278152), new Type(159, (byte)0)); // WhiteStainedClay
        types.put(new Vector3D(0.62357298474945533, 0.3216557734204793, 0.14087145969498913), new Type(159, (byte)1)); // OrangeStainedClay
        types.put(new Vector3D(0.58388977212506632, 0.33995760466348701, 0.42193958664546899), new Type(159, (byte)2)); // MagentaStainedClay
        types.put(new Vector3D(0.44742647058823531, 0.42655228758169933, 0.54093137254901957), new Type(159, (byte)3)); // LightBlueStainedClay
        types.put(new Vector3D(0.72912127814088601, 0.5242314209634471, 0.14093439845073832), new Type(159, (byte)4)); // YellowStainedClay
        types.put(new Vector3D(0.40369747899159664, 0.45848739495798319, 0.20582633053221289), new Type(159, (byte)5)); // LimeStainedClay
        types.put(new Vector3D(0.62470588235294122, 0.29588235294117649, 0.29843137254901958), new Type(159, (byte)6)); // PinkStainedClay
        types.put(new Vector3D(0.22758316809870013, 0.16761401189689359, 0.14126459572593081), new Type(159, (byte)7)); // GrayStainedClay
        types.put(new Vector3D(0.52941176470588236, 0.42006251776072745, 0.38351804489911906), new Type(159, (byte)8)); // LightGrayStainedClay
        types.put(new Vector3D(0.33498152884342142, 0.35083830633702756, 0.35197499289570899), new Type(159, (byte)9)); // CyanStainedClay
        types.put(new Vector3D(0.45104468016714883, 0.26615236258437802, 0.32838315654130507), new Type(159, (byte)10)); // PurpleStainedClay
        types.put(new Vector3D(0.28946078431372552, 0.23127450980392159, 0.35593137254901963), new Type(159, (byte)11)); // BlueStainedClay
        types.put(new Vector3D(0.30338680926916217, 0.20277282630223806, 0.14117647058823529), new Type(159, (byte)12)); // BrownStainedClay
        types.put(new Vector3D(0.29448366013071897, 0.32172549019607843, 0.1621437908496732), new Type(159, (byte)13)); // GreenStainedClay
        types.put(new Vector3D(0.55511982570806095, 0.23238925199709515, 0.17966594045025419), new Type(159, (byte)14)); // RedStainedClay
        types.put(new Vector3D(0.14509803921568629, 0.086090686274509803, 0.06145833333333333), new Type(159, (byte)15)); // BlackStainedClay

        types.put(new Vector3D(0.30679100908656148, 0.28660927785748441, 0.25858440937350552), new Type(162, (byte)0)); // AcaciaWood
        types.put(new Vector3D(0.20858823529411763, 0.16239215686274508, 0.093137254901960786), new Type(162, (byte)1)); // DarkOakWood

        types.put(new Vector3D(0.51277480689245392, 0.8160427807486631, 0.43547237076648843), new Type(165, (byte)0)); // Slime

        types.put(new Vector3D(0.29542850848204449, 0.54230006609385328, 0.4892707644855695), new Type(168, (byte)0)); // Prismarine
        types.put(new Vector3D(0.47938785270205647, 0.70707795313247257, 0.64270683883309421), new Type(168, (byte)1)); // PrismarineBricks
        types.put(new Vector3D(0.20329909741674448, 0.30687830687830686, 0.25969498910675382), new Type(168, (byte)2)); // DarkPrismarine
        types.put(new Vector3D(0.87702519760688236, 0.91413598847111233, 0.87987248351456393), new Type(169, (byte)0)); // SeaLantern

        types.put(new Vector3D(0.70893246187363834, 0.58741830065359468, 0.061764705882352944), new Type(170, (byte)0)); // HayBale

        types.put(new Vector3D(0.58708898944193055, 0.36006033182503772, 0.25954751131221721), new Type(172, (byte)0)); // HardenedClay

        types.put(new Vector3D(0.04665314401622718, 0.04665314401622718, 0.04665314401622718), new Type(173, (byte)0)); // CoalBlock

        types.put(new Vector3D(0.66175554333883091, 0.78185816382627815, 0.98834524463991202), new Type(174, (byte)0)); // PackedIce

        types.put(new Vector3D(0.64345521992580823, 0.32750397456279812, 0.11478537360890302), new Type(179, (byte)0)); // RedSandstone
    }

    public static ColorMatcher getInstance() {
        if (instance == null)
            instance = new ColorMatcher();
        return instance;
    }

    public Type getDefaultType() {
        return this.defaultType;
    }

    // Returns the best match of a block type from color
    // note: this is the simplest check, but not the most accurate
    public Type getTypeFromColor(Vector3D color) {
        double best_dist = Double.MAX_VALUE;
        Type best_match = null;

        for (Map.Entry<Vector3D, Type> entry : types.entrySet()) {
            double dist = entry.getKey().distance(color);

            if (dist < best_dist) {
                best_dist = dist;
                best_match = entry.getValue();
            }
        }

        if (best_match == null)
            return new Type(1, (byte)0);

        return best_match;
    }

    public class Type {
        public int id;
        public byte data;

        public Type(int id, byte data) {
            this.id = id;
            this.data = data;
        }
    }
}
