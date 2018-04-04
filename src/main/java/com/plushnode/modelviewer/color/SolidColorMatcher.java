package com.plushnode.modelviewer.color;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class SolidColorMatcher extends ColorMatcher {
    public SolidColorMatcher() {
        types.put(new Vector3D(0.58708898944193055, 0.36006033182503772, 0.25954751131221721), new ColorType(172, (byte)0)); // HardenedClay

        types.put(new Vector3D(0.82156862745098036, 0.6942088463292293, 0.63096215230278152), new ColorType(159, (byte)0)); // WhiteStainedClay
        types.put(new Vector3D(0.6229019607843137, 0.32073202614379082, 0.13997385620915034), new ColorType(159, (byte)1)); // OrangeStainedClay
        types.put(new Vector3D(0.58388977212506632, 0.33995760466348701, 0.42193958664546899), new ColorType(159, (byte)2)); // MagentaStainedClay
        types.put(new Vector3D(0.44705882352941179, 0.4243612596553773, 0.54046345811051688), new ColorType(159, (byte)3)); // LightBlueStainedClay
        types.put(new Vector3D(0.72563366810138685, 0.51516021042563365, 0.13443328550932568), new ColorType(159, (byte)4)); // YellowStainedClay
        types.put(new Vector3D(0.40369747899159664, 0.45848739495798319, 0.20582633053221289), new ColorType(159, (byte)5)); // LimeStainedClay
        types.put(new Vector3D(0.62957516339869279, 0.30206971677559913, 0.30430283224400873), new ColorType(159, (byte)6)); // PinkStainedClay
        types.put(new Vector3D(0.22745098039215686, 0.16880570409982176, 0.14111705288175877), new ColorType(159, (byte)7)); // GrayStainedClay
        types.put(new Vector3D(0.52862745098039221, 0.41522875816993465, 0.37869281045751635), new ColorType(159, (byte)8)); // LightGrayStainedClay
        types.put(new Vector3D(0.33536332179930795, 0.35141868512110724, 0.35266435986159167), new ColorType(159, (byte)9)); // CyanStainedClay
        types.put(new Vector3D(0.45104468016714883, 0.26615236258437802, 0.32838315654130507), new ColorType(159, (byte)10)); // PurpleStainedClay
        types.put(new Vector3D(0.28946078431372552, 0.23127450980392159, 0.35593137254901963), new ColorType(159, (byte)11)); // BlueStainedClay
        types.put(new Vector3D(0.30338680926916217, 0.20277282630223806, 0.14117647058823529), new ColorType(159, (byte)12)); // BrownStainedClay
        types.put(new Vector3D(0.29380392156862745, 0.32141176470588234, 0.16070588235294117), new ColorType(159, (byte)13)); // GreenStainedClay
        types.put(new Vector3D(0.55514108082257296, 0.23242467718794835, 0.17972262075561932), new ColorType(159, (byte)14)); // RedStainedClay
        types.put(new Vector3D(0.14518059855521157, 0.08771929824561403, 0.062084623323013413), new ColorType(159, (byte)15)); // BlackStainedClay

        types.put(new Vector3D(0.81176470588235294, 0.83529411764705885, 0.83921568627450982), new ColorType(251, (byte)0)); // WhiteConcrete
        types.put(new Vector3D(0.88235294117647056, 0.38039215686274508, 0.0), new ColorType(251, (byte)1)); // OrangeConcrete
        types.put(new Vector3D(0.66274509803921566, 0.18823529411764706, 0.62352941176470589), new ColorType(251, (byte)2)); // MagentaConcrete
        types.put(new Vector3D(0.13725490196078433, 0.53725490196078429, 0.78029160382101559), new ColorType(251, (byte)3)); // LightBlueConcrete
        types.put(new Vector3D(0.94901960784313721, 0.69050980392156869, 0.084026143790849675), new ColorType(251, (byte)4)); // YellowConcrete
        types.put(new Vector3D(0.36862745098039218, 0.66274509803921566, 0.094117647058823528), new ColorType(251, (byte)5)); // LimeConcrete
        types.put(new Vector3D(0.83921568627450982, 0.39612403100775195, 0.5607843137254902), new ColorType(251, (byte)6)); // PinkConcrete
        types.put(new Vector3D(0.21568627450980393, 0.22745098039215686, 0.24313725490196078), new ColorType(251, (byte)7)); // GrayConcrete
        types.put(new Vector3D(0.49019607843137253, 0.49019607843137253, 0.45098039215686275), new ColorType(251, (byte)8)); // LightGrayConcrete
        types.put(new Vector3D(0.082352941176470587, 0.46666666666666667, 0.53333333333333333), new ColorType(251, (byte)9)); // CyanConcrete
        types.put(new Vector3D(0.396078431372549, 0.12549019607843137, 0.61568627450980395), new ColorType(251, (byte)10)); // PurpleConcrete
        types.put(new Vector3D(0.17254901960784313, 0.1803921568627451, 0.56034203154946183), new ColorType(251, (byte)11)); // BlueConcrete
        types.put(new Vector3D(0.38039215686274508, 0.23529411764705882, 0.12549019607843137), new ColorType(251, (byte)12)); // BrownConcrete
        types.put(new Vector3D(0.29019607843137257, 0.36078431372549019, 0.14543610547667343), new ColorType(251, (byte)13)); // GreenConcrete
        types.put(new Vector3D(0.55686274509803924, 0.12549019607843137, 0.12549019607843137), new ColorType(251, (byte)14)); // RedConcrete
        types.put(new Vector3D(0.035294117647058823, 0.043137254901960784, 0.062745098039215685), new ColorType(251, (byte)15)); // BlackConcrete

        convertTypesToCIELab();
    }
}
