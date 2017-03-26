package com.plushnode.modelviewer.util;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class ClayColorMatcher extends ColorMatcher {
    public ClayColorMatcher() {
        types.put(new Vector3D(0.82156862745098036, 0.6942088463292293, 0.63096215230278152), new ColorType(159, (byte) 0)); // WhiteStainedClay
        types.put(new Vector3D(0.62357298474945533, 0.3216557734204793, 0.14087145969498913), new ColorType(159, (byte) 1)); // OrangeStainedClay
        types.put(new Vector3D(0.58388977212506632, 0.33995760466348701, 0.42193958664546899), new ColorType(159, (byte) 2)); // MagentaStainedClay
        types.put(new Vector3D(0.44742647058823531, 0.42655228758169933, 0.54093137254901957), new ColorType(159, (byte) 3)); // LightBlueStainedClay
        types.put(new Vector3D(0.72912127814088601, 0.5242314209634471, 0.14093439845073832), new ColorType(159, (byte) 4)); // YellowStainedClay
        types.put(new Vector3D(0.40369747899159664, 0.45848739495798319, 0.20582633053221289), new ColorType(159, (byte) 5)); // LimeStainedClay
        types.put(new Vector3D(0.62470588235294122, 0.29588235294117649, 0.29843137254901958), new ColorType(159, (byte) 6)); // PinkStainedClay
        types.put(new Vector3D(0.22758316809870013, 0.16761401189689359, 0.14126459572593081), new ColorType(159, (byte) 7)); // GrayStainedClay
        types.put(new Vector3D(0.52941176470588236, 0.42006251776072745, 0.38351804489911906), new ColorType(159, (byte) 8)); // LightGrayStainedClay
        types.put(new Vector3D(0.33498152884342142, 0.35083830633702756, 0.35197499289570899), new ColorType(159, (byte) 9)); // CyanStainedClay
        types.put(new Vector3D(0.45104468016714883, 0.26615236258437802, 0.32838315654130507), new ColorType(159, (byte) 10)); // PurpleStainedClay
        types.put(new Vector3D(0.28946078431372552, 0.23127450980392159, 0.35593137254901963), new ColorType(159, (byte) 11)); // BlueStainedClay
        types.put(new Vector3D(0.30338680926916217, 0.20277282630223806, 0.14117647058823529), new ColorType(159, (byte) 12)); // BrownStainedClay
        types.put(new Vector3D(0.29448366013071897, 0.32172549019607843, 0.1621437908496732), new ColorType(159, (byte) 13)); // GreenStainedClay
        types.put(new Vector3D(0.55511982570806095, 0.23238925199709515, 0.17966594045025419), new ColorType(159, (byte) 14)); // RedStainedClay
        types.put(new Vector3D(0.14509803921568629, 0.086090686274509803, 0.06145833333333333), new ColorType(159, (byte) 15)); // BlackStainedClay
    }
}
