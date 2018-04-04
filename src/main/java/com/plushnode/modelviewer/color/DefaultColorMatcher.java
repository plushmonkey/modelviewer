package com.plushnode.modelviewer.color;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class DefaultColorMatcher extends ColorMatcher {
    public DefaultColorMatcher() {
        byte barkOnly = 3 << 2;

        types.put(new Vector3D(0.44762923351158646, 0.44762923351158646, 0.44762923351158646), new ColorType(1, (byte)0)); // Stone
        //types.put(new Vector3D(0.41721884155961236, 0.3054766734279919, 0.26306062654947038), new ColorType(1, (byte)1)); // Granite
        types.put(new Vector3D(0.60754901960784313, 0.41191176470588231, 0.34058823529411764), new ColorType(1, (byte)2)); // Polished Granite
        types.put(new Vector3D(0.60501960784313724, 0.60501960784313724, 0.61772549019607847), new ColorType(1, (byte)3)); // Diorite
        types.put(new Vector3D(0.80475490196078436, 0.80475490196078436, 0.8175980392156863), new ColorType(1, (byte)4)); // Polished Diorite
        types.put(new Vector3D(0.41296732026143795, 0.41328104575163399, 0.4169411764705882), new ColorType(1, (byte)5)); // Andesite
        types.put(new Vector3D(0.51139448786507613, 0.51216234745646516, 0.52156862745098043), new ColorType(1, (byte)6)); // Polished Andesite

        types.put(new Vector3D(0.4743282498184459, 0.33344226579520697, 0.22781408859840233), new ColorType(3, (byte)0)); // Dirt

        types.put(new Vector3D(0.49880315762668703, 0.49880315762668703, 0.49880315762668703), new ColorType(4, (byte)0)); // Cobblestone

        //types.put(new Vector3D(0.73725490196078436, 0.59607843137254901, 0.3843137254901961), new ColorType(5, (byte)0)); // OakPlank
        //types.put(new Vector3D(0.50196078431372548, 0.36862745098039218, 0.21176470588235294), new ColorType(5, (byte)1)); // SprucePlank
        //types.put(new Vector3D(0.7686274509803922, 0.70241327300150824, 0.47194570135746605), new ColorType(5, (byte)2)); // BirchPlank
        //types.put(new Vector3D(0.72156862745098038, 0.52941176470588236, 0.39215686274509803), new ColorType(5, (byte)3)); // JunglePlank
        //types.put(new Vector3D(0.72941176470588232, 0.40113519091847261, 0.2260061919504644), new ColorType(5, (byte)4)); // AcaciaPlank
        //types.put(new Vector3D(0.28627450980392155, 0.18431372549019609, 0.090196078431372548), new ColorType(5, (byte)5)); // DarkOakPlank

        //types.put(new Vector3D(0.40862745098039216, 0.3268627450980392, 0.19886274509803922), new ColorType(17, (byte)barkOnly)); // OakWood
        //types.put(new Vector3D(0.078813964610234336, 0.036633189861310381, 0.014155906264945003), new ColorType(17, (byte)(1 | barkOnly))); // SpruceWood
        //types.put(new Vector3D(0.91787969425058158, 0.91811232967763379, 0.8910933865071452), new ColorType(17, (byte)(2 | barkOnly))); // BirchWood
        //types.put(new Vector3D(0.23484735666418466, 0.18346984363365601, 0.074708364358401586), new ColorType(17, (byte)(3 | barkOnly))); // JungleWood

        types.put(new Vector3D(0.16145510835913313, 0.25701754385964909, 0.4643446852425181), new ColorType(22, (byte)0)); // LapisBlock

        types.put(new Vector3D(0.92336134453781515, 0.89098039215686275, 0.72089635854341738), new ColorType(24, (byte)0)); // Sandstone
        types.put(new Vector3D(0.85088356330186399, 0.81955942870975551, 0.62619220527717256), new ColorType(24, (byte)1)); // ChiseledSandstone
        types.put(new Vector3D(0.86808278867102395, 0.83447712418300646, 0.6495642701525054), new ColorType(24, (byte)2)); // SmoothSandstone

        types.put(new Vector3D(0.98517156862745103, 0.98639705882352946, 0.98639705882352946), new ColorType(35, (byte)0)); // WhiteWool
        types.put(new Vector3D(0.93162109914388291, 0.43463131731565868, 0.052416459541563105), new ColorType(35, (byte)1)); // OrangeWool
        types.put(new Vector3D(0.75548764960529668, 0.27934810287751466, 0.71448943213649097), new ColorType(35, (byte)2)); // MagentaWool
        types.put(new Vector3D(0.29270833333333335, 0.76384803921568623, 0.89865196078431375), new ColorType(35, (byte)3)); // LightBlueWool
        types.put(new Vector3D(0.96851698425849209, 0.75183650925158796, 0.12841756420878211), new ColorType(35, (byte)4)); // YellowWool
        types.put(new Vector3D(0.41629384148025406, 0.70588235294117652, 0.094117647058823528), new ColorType(35, (byte)5)); // LimeWool
        types.put(new Vector3D(0.95686274509803926, 0.60098039215686272, 0.70986928104575164), new ColorType(35, (byte)6)); // PinkWool
        types.put(new Vector3D(0.2351836509251588, 0.25368682684341343, 0.26716376691521682), new ColorType(35, (byte)7)); // GrayWool
        types.put(new Vector3D(0.60600490196078427, 0.60600490196078427, 0.58204656862745097), new ColorType(35, (byte)8)); // LightGrayWool
        types.put(new Vector3D(0.082352941176470587, 0.52233115468409586, 0.55882352941176472), new ColorType(35, (byte)9)); // CyanWool
        types.put(new Vector3D(0.44878193701723113, 0.14610814022578728, 0.65038621509209738), new ColorType(35, (byte)10)); // PurpleWool
        types.put(new Vector3D(0.19536541889483067, 0.20483829895594602, 0.59439775910364145), new ColorType(35, (byte)11)); // BlueWool
        types.put(new Vector3D(0.42479638009049775, 0.26419306184012065, 0.14624434389140273), new ColorType(35, (byte)12)); // BrownWool
        types.put(new Vector3D(0.31355742296918765, 0.40056022408963582, 0.11781512605042017), new ColorType(35, (byte)13)); // GreenWool
        types.put(new Vector3D(0.57732843137254897, 0.13247549019607843, 0.12726715686274509), new ColorType(35, (byte)14)); // RedWool
        types.put(new Vector3D(0.061064425770308121, 0.065444359562006629, 0.083269671504965614), new ColorType(35, (byte)15)); // BlackWool

        types.put(new Vector3D(0.99512605042016811, 0.97641456582633057, 0.34011204481792717), new ColorType(41, (byte)0)); // GoldBlock
        types.put(new Vector3D(0.90269378779549203, 0.90269378779549203, 0.90269378779549203), new ColorType(42, (byte)0)); // IronBlock

        types.put(new Vector3D(0.063211026985051447, 0.06309454474859251, 0.10021355076684139), new ColorType(49, (byte)0)); // Obsidian

        types.put(new Vector3D(0.016384636046199302, 0.75895782970722536, 0.72060166532366376), new ColorType(57, (byte)0)); // DiamondBlock

        types.put(new Vector3D(0.93333333333333335, 1.0, 1.0), new ColorType(80, (byte)0)); // SnowBlock

        types.put(new Vector3D(0.60902433262461608, 0.63283723127805336, 0.67649421214268846), new ColorType(82, (byte)0)); // ClayBlock

        //types.put(new Vector3D(0.37597348798674396, 0.19850869925434961, 0.19784589892294946), new ColorType(87, (byte)0)); // Netherrack
        //types.put(new Vector3D(0.40209508460918614, 0.31485361267794792, 0.26016653236637122), new ColorType(88, (byte)0)); // SoulSand

        types.put(new Vector3D(0.1101419878296146, 0.056862745098039215, 0.067748478701825557), new ColorType(112, (byte)0)); // NetherBrick
        types.put(new Vector3D(0.47932773109243698, 0.026928104575163397, 0.031297852474323061), new ColorType(214, (byte)0)); // NetherWartBlock
        types.put(new Vector3D(0.85509803921568628, 0.8728104575163399, 0.62653594771241838), new ColorType(121, (byte)0)); // EndStone

        types.put(new Vector3D(0.2939911448450348, 0.86805819101834281, 0.45249841872232766), new ColorType(133, (byte)0)); // EmeraldBlock

        types.put(new Vector3D(0.47722473604826549, 0.063499245852187039, 0.02393162393162393), new ColorType(152, (byte)0)); // RedstoneBlock

        types.put(new Vector3D(0.94125990821860661, 0.93299958281184814, 0.90917813934084268), new ColorType(155, (byte)0)); // QuartzBlock

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

        //types.put(new Vector3D(0.30679100908656148, 0.28660927785748441, 0.25858440937350552), new ColorType(162, barkOnly)); // AcaciaWood
        //types.put(new Vector3D(0.20858823529411763, 0.16239215686274508, 0.093137254901960786), new ColorType(162, (byte)(1 | barkOnly))); // DarkOakWood

        types.put(new Vector3D(0.51023965141612204, 0.81360445412732996, 0.43379327039457755), new ColorType(165, (byte)0)); // Slime

        types.put(new Vector3D(0.29585324325808759, 0.54297797550392712, 0.48959191519745154), new ColorType(168, (byte)0)); // Prismarine
        types.put(new Vector3D(0.47544563279857394, 0.70530303030303032, 0.64090909090909098), new ColorType(168, (byte)1)); // PrismarineBricks
        types.put(new Vector3D(0.20465686274509803, 0.30814950980392158, 0.26115196078431374), new ColorType(168, (byte)2)); // DarkPrismarine
        //types.put(new Vector3D(0.87715927534324689, 0.91418169057332099, 0.87988770452252496), new ColorType(169, (byte)0)); // SeaLantern

        //types.put(new Vector3D(0.70893246187363834, 0.58741830065359468, 0.061764705882352944), new ColorType(170, (byte)0)); // HayBale

        types.put(new Vector3D(0.58708898944193055, 0.36006033182503772, 0.25954751131221721), new ColorType(172, (byte)0)); // HardenedClay

        types.put(new Vector3D(0.050980392156862744, 0.050980392156862744, 0.050980392156862744), new ColorType(173, (byte)0)); // CoalBlock

        types.put(new Vector3D(0.66175554333883091, 0.78185816382627815, 0.98834524463991202), new ColorType(174, (byte)0)); // PackedIce

        types.put(new Vector3D(0.64345521992580823, 0.32750397456279812, 0.11478537360890302), new ColorType(179, (byte)0)); // RedSandstone

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

    /*private void pre_1_12() {
        byte barkOnly = 3 << 2;

        types.put(new Vector3D(0.45490196078431372, 0.45490196078431372, 0.45490196078431372), new ColorType(1, (byte)0)); // Stone
        types.put(new Vector3D(0.64131994261119085, 0.44184600669536106, 0.36747967479674798), new ColorType(1, (byte)1)); // Granite
        types.put(new Vector3D(0.62264279624893437, 0.42605285592497871, 0.35345268542199493), new ColorType(1, (byte)2)); // Polished Granite
        types.put(new Vector3D(0.60609432962374143, 0.60609432962374143, 0.61886592474827773), new ColorType(1, (byte)3)); // Diorite
        types.put(new Vector3D(0.80475490196078436, 0.80475490196078436, 0.8175980392156863), new ColorType(1, (byte)4)); // Polished Diorite
        types.put(new Vector3D(0.41296732026143795, 0.41328104575163399, 0.4169411764705882), new ColorType(1, (byte)5)); // Andesite
        types.put(new Vector3D(0.51139448786507613, 0.51216234745646516, 0.52156862745098043), new ColorType(1, (byte)6)); // Polished Andesite

        //types.put(new Vector3D(0.58795518207282904, 0.58795518207282904, 0.58795518207282904), new ColorType(2, (byte)0)); // Grass

        types.put(new Vector3D(0.4743282498184459, 0.33344226579520697, 0.22781408859840233), new ColorType(3, (byte)0)); // Dirt
        //types.put(new Vector3D(0.46421073479897007, 0.33345216874628641, 0.233630421865716), new ColorType(3, (byte)1)); // Coarse Dirt
        //types.put(new Vector3D(0.2826220684352172, 0.20319108035371011, 0.10830449826989619), new ColorType(3, (byte)2)); // Podzol

        types.put(new Vector3D(0.49936481634907481, 0.49936481634907481, 0.49936481634907481), new ColorType(4, (byte)0)); // Cobblestone

        types.put(new Vector3D(0.73725490196078436, 0.59607843137254901, 0.3843137254901961), new ColorType(5, (byte)0)); // OakPlank
        types.put(new Vector3D(0.50196078431372548, 0.36862745098039218, 0.21176470588235294), new ColorType(5, (byte)1)); // SprucePlank
        types.put(new Vector3D(0.84313725490196079, 0.78266253869969038, 0.54220846233230136), new ColorType(5, (byte)2)); // BirchPlank
        types.put(new Vector3D(0.72156862745098038, 0.52941176470588236, 0.39215686274509803), new ColorType(5, (byte)3)); // JunglePlank
        types.put(new Vector3D(0.72941176470588232, 0.40113519091847261, 0.2260061919504644), new ColorType(5, (byte)4)); // AcaciaPlank
        types.put(new Vector3D(0.28627450980392155, 0.18431372549019609, 0.090196078431372548), new ColorType(5, (byte)5)); // DarkOakPlank

        //types.put(new Vector3D(0.20000000000000001, 0.20000000000000001, 0.20000000000000001), new ColorType(7, (byte)0)); // Bedrock

        //types.put(new Vector3D(0.86487275761368376, 0.83425114726741767, 0.6299123904881101), new ColorType(12, (byte)0)); // Sand
        //types.put(new Vector3D(0.64143633356957241, 0.32808882589180249, 0.11802504134183794), new ColorType(12, (byte)1)); // RedSand

        //types.put(new Vector3D(0.50544662309368193, 0.49774872912127816, 0.49750665698378121), new ColorType(13, (byte)0)); // Gravel
        //types.put(new Vector3D(0.52426872388299572, 0.52426872388299572, 0.52426872388299572), new ColorType(14, (byte)0)); // GoldOre
        //types.put(new Vector3D(0.45490196078431372, 0.45490196078431372, 0.45490196078431372), new ColorType(15, (byte)0)); // IronOre
        //types.put(new Vector3D(0.44810996563573885, 0.44810996563573885, 0.44810996563573885), new ColorType(16, (byte)0)); // CoalOre

        types.put(new Vector3D(0.40862745098039216, 0.3268627450980392, 0.19886274509803922), new ColorType(17, (byte)(0 | barkOnly))); // OakWood
        types.put(new Vector3D(0.078813964610234336, 0.036633189861310381, 0.014155906264945003), new ColorType(17, (byte)(1 | barkOnly))); // SpruceWood
        types.put(new Vector3D(0.91787969425058158, 0.91811232967763379, 0.8910933865071452), new ColorType(17, (byte)(2 | barkOnly))); // BirchWood
        types.put(new Vector3D(0.23484735666418466, 0.18346984363365601, 0.074708364358401586), new ColorType(17, (byte)(3 | barkOnly))); // JungleWood

        //types.put(new Vector3D(0.63941176470588235, 0.63878431372549016, 0.6366274509803922), new ColorType(18, (byte)3)); // JungleLeaves

        types.put(new Vector3D(0.77996589940323957, 0.78303495311167937, 0.3495737425404945), new ColorType(19, (byte)0)); // Sponge
        types.put(new Vector3D(0.61568627450980395, 0.60009119927040577, 0.19913360693114454), new ColorType(19, (byte)1)); // SpongeWet

        //types.put(new Vector3D(0.0, 0.0, 0.0), new ColorType(20, (byte)0)); // Glass

        ////types.put(new Vector3D(0.44817927170868349, 0.44817927170868349, 0.44817927170868349), new ColorType(21, (byte)0)); // LapisOre
        types.put(new Vector3D(0.15065847234416155, 0.26286215978928884, 0.53064091308165051), new ColorType(22, (byte)0)); // LapisBlock

        types.put(new Vector3D(0.92336134453781515, 0.89098039215686275, 0.72089635854341738), new ColorType(24, (byte)0)); // Sandstone
        types.put(new Vector3D(0.84925872788139645, 0.81802965088474411, 0.62329029172644668), new ColorType(24, (byte)1)); // ChiseledSandstone
        types.put(new Vector3D(0.86808278867102395, 0.83447712418300646, 0.6495642701525054), new ColorType(24, (byte)2)); // SmoothSandstone

        //types.put(new Vector3D(0.57808535178777387, 0.37151095732410611, 0.27035755478662049), new ColorType(25, (byte)0)); // NoteBlock

        types.put(new Vector3D(0.82908496732026138, 0.82908496732026138, 0.82908496732026138), new ColorType(35, (byte)0)); // WhiteWool
        types.put(new Vector3D(0.86588235294117655, 0.51423298731257205, 0.28023068050749711), new ColorType(35, (byte)1)); // OrangeWool
        types.put(new Vector3D(0.68881199538638993, 0.27118800461361015, 0.72535178777393317), new ColorType(35, (byte)2)); // MagentaWool
        types.put(new Vector3D(0.44857142857142857, 0.56487394957983195, 0.80084033613445382), new ColorType(35, (byte)3)); // LightBlueWool
        types.put(new Vector3D(0.65082559339525281, 0.609391124871001, 0.14019607843137255), new ColorType(35, (byte)4)); // YellowWool
        types.put(new Vector3D(0.24147058823529413, 0.64931372549019606, 0.20813725490196081), new ColorType(35, (byte)5)); // LimeWool
        types.put(new Vector3D(0.80076252723311547, 0.47625272331154683, 0.56497821350762534), new ColorType(35, (byte)6)); // PinkWool
        types.put(new Vector3D(0.23766915216790943, 0.23766915216790943, 0.23766915216790943), new ColorType(35, (byte)7)); // GrayWool
        types.put(new Vector3D(0.62834733893557426, 0.65187675070028006, 0.65187675070028006), new ColorType(35, (byte)8)); // LightGrayWool
        types.put(new Vector3D(0.18862745098039216, 0.4486274509803922, 0.55613445378151261), new ColorType(35, (byte)9)); // CyanWool
        types.put(new Vector3D(0.51204481792717094, 0.24330532212885156, 0.7415686274509804), new ColorType(35, (byte)10)); // PurpleWool
        types.put(new Vector3D(0.1741830065359477, 0.21590413943355122, 0.53594771241830064), new ColorType(35, (byte)11)); // BlueWool
        types.put(new Vector3D(0.29401960784313724, 0.18593137254901962, 0.11490196078431372), new ColorType(35, (byte)12)); // BrownWool
        types.put(new Vector3D(0.21492233256939139, 0.28724216959511079, 0.10970206264323912), new ColorType(35, (byte)13)); // GreenWool
        types.put(new Vector3D(0.61315233785822021, 0.21242835595776774, 0.19770739064856713), new ColorType(35, (byte)14)); // RedWool
        types.put(new Vector3D(0.10478240076518412, 0.090626494500239116, 0.090626494500239116), new ColorType(35, (byte)15)); // BlackWool

        types.put(new Vector3D(0.99512605042016811, 0.97641456582633057, 0.34011204481792717), new ColorType(41, (byte)0)); // GoldBlock
        types.put(new Vector3D(0.90269378779549203, 0.90269378779549203, 0.90269378779549203), new ColorType(42, (byte)0)); // IronBlock

        //types.put(new Vector3D(0.63597025016903319, 0.63597025016903319, 0.63597025016903319), new ColorType(43, (byte)0)); // DoubleStoneSlab

        //types.put(new Vector3D(0.47323529411764703, 0.26294117647058823, 0.20568627450980392), new ColorType(45, (byte)0)); // Brick

        types.put(new Vector3D(0.2242467718794835, 0.3573888091822095, 0.2242467718794835), new ColorType(48, (byte)0)); // MossStone
        types.put(new Vector3D(0.063211026985051447, 0.06309454474859251, 0.10021355076684139), new ColorType(49, (byte)0)); // Obsidian

        //types.put(new Vector3D(0.47478991596638653, 0.47478991596638653, 0.47478991596638653), new ColorType(56, (byte)0)); // DiamondOre

        types.put(new Vector3D(0.51625386996904021, 0.89618163054695554, 0.88049535603715168), new ColorType(57, (byte)0)); // DiamondBlock

        //types.put(new Vector3D(0.12902513117923226, 0.10378348522507595, 0.060425296879315103), new ColorType(58, (byte)0)); // CraftingTable

        //types.put(new Vector3D(0.3880099166103223, 0.24719405003380662, 0.14162722560288482), new ColorType(60, (byte)0)); // FarmLand

        //types.put(new Vector3D(0.44810996563573885, 0.44810996563573885, 0.44810996563573885), new ColorType(73, (byte)0)); // RedstoneOre

        //types.put(new Vector3D(0.47143312609596688, 0.66730432010202456, 1.0), new ColorType(79, (byte)0)); // Ice
        types.put(new Vector3D(0.93333333333333335, 1.0, 1.0), new ColorType(80, (byte)0)); // SnowBlock

        types.put(new Vector3D(0.60902433262461608, 0.63283723127805336, 0.67649421214268846), new ColorType(82, (byte)0)); // ClayBlock

        //types.put(new Vector3D(0.62745098039215685, 0.35294117647058826, 0.043137254901960784), new ColorType(86, (byte)0)); // Pumpkin

        types.put(new Vector3D(0.37597348798674396, 0.19850869925434961, 0.19784589892294946), new ColorType(87, (byte)0)); // Netherrack
        types.put(new Vector3D(0.40209508460918614, 0.31485361267794792, 0.26016653236637122), new ColorType(88, (byte)0)); // SoulSand
        //types.put(new Vector3D(0.44705882352941179, 0.43529411764705883, 0.28627450980392155), new ColorType(89, (byte)0)); // Glowstone

        //types.put(new Vector3D(0.5628011204481792, 0.42162464985994402, 0.32750700280112049), new ColorType(99, (byte)0)); // BrownMushroom
        //types.put(new Vector3D(0.70983297022512715, 0.11375453885257807, 0.10591140159767611), new ColorType(100, (byte)0)); // RedMushroom

        //types.put(new Vector3D(0.7070223438212494, 0.70337437300501593, 0.15740994072047423), new ColorType(103, (byte)0)); // Melon

        //types.put(new Vector3D(0.41943119590178413, 0.38014485073308601, 0.40332096802685036), new ColorType(110, (byte)0)); // mycelium
        types.put(new Vector3D(0.18992458521870287, 0.094358974358974362, 0.11046757164404224), new ColorType(112, (byte)0)); // NetherBrick
        types.put(new Vector3D(0.85509803921568628, 0.8728104575163399, 0.62653594771241838), new ColorType(121, (byte)0)); // EndStone

        //types.put(new Vector3D(0.49431096382214856, 0.49646506489919912, 0.49513946423639882), new ColorType(129, (byte)0)); // EmeraldOre
        types.put(new Vector3D(0.3871936274509804, 0.91158088235294121, 0.53541666666666665), new ColorType(133, (byte)0)); // EmeraldBlock

        //types.put(new Vector3D(0.16862745098039217, 0.77459505541346974, 0.73384484228474001), new ColorType(138, (byte)0)); // Beacon

        types.put(new Vector3D(0.47722473604826549, 0.063499245852187039, 0.02393162393162393), new ColorType(152, (byte)0)); // RedstoneBlock

        types.put(new Vector3D(0.94125990821860661, 0.93299958281184814, 0.90917813934084268), new ColorType(155, (byte)0)); // QuartzBlock

        types.put(new Vector3D(0.82156862745098036, 0.6942088463292293, 0.63096215230278152), new ColorType(159, (byte)0)); // WhiteStainedClay
        types.put(new Vector3D(0.62357298474945533, 0.3216557734204793, 0.14087145969498913), new ColorType(159, (byte)1)); // OrangeStainedClay
        types.put(new Vector3D(0.58388977212506632, 0.33995760466348701, 0.42193958664546899), new ColorType(159, (byte)2)); // MagentaStainedClay
        types.put(new Vector3D(0.44742647058823531, 0.42655228758169933, 0.54093137254901957), new ColorType(159, (byte)3)); // LightBlueStainedClay
        types.put(new Vector3D(0.72912127814088601, 0.5242314209634471, 0.14093439845073832), new ColorType(159, (byte)4)); // YellowStainedClay
        types.put(new Vector3D(0.40369747899159664, 0.45848739495798319, 0.20582633053221289), new ColorType(159, (byte)5)); // LimeStainedClay
        types.put(new Vector3D(0.62470588235294122, 0.29588235294117649, 0.29843137254901958), new ColorType(159, (byte)6)); // PinkStainedClay
        types.put(new Vector3D(0.22758316809870013, 0.16761401189689359, 0.14126459572593081), new ColorType(159, (byte)7)); // GrayStainedClay
        types.put(new Vector3D(0.52941176470588236, 0.42006251776072745, 0.38351804489911906), new ColorType(159, (byte)8)); // LightGrayStainedClay
        types.put(new Vector3D(0.33498152884342142, 0.35083830633702756, 0.35197499289570899), new ColorType(159, (byte)9)); // CyanStainedClay
        types.put(new Vector3D(0.45104468016714883, 0.26615236258437802, 0.32838315654130507), new ColorType(159, (byte)10)); // PurpleStainedClay
        types.put(new Vector3D(0.28946078431372552, 0.23127450980392159, 0.35593137254901963), new ColorType(159, (byte)11)); // BlueStainedClay
        types.put(new Vector3D(0.30338680926916217, 0.20277282630223806, 0.14117647058823529), new ColorType(159, (byte)12)); // BrownStainedClay
        types.put(new Vector3D(0.29448366013071897, 0.32172549019607843, 0.1621437908496732), new ColorType(159, (byte)13)); // GreenStainedClay
        types.put(new Vector3D(0.55511982570806095, 0.23238925199709515, 0.17966594045025419), new ColorType(159, (byte)14)); // RedStainedClay
        types.put(new Vector3D(0.14509803921568629, 0.086090686274509803, 0.06145833333333333), new ColorType(159, (byte)15)); // BlackStainedClay

        types.put(new Vector3D(0.30679100908656148, 0.28660927785748441, 0.25858440937350552), new ColorType(162, (byte)(0 | barkOnly))); // AcaciaWood
        types.put(new Vector3D(0.20858823529411763, 0.16239215686274508, 0.093137254901960786), new ColorType(162, (byte)(1 | barkOnly))); // DarkOakWood

        types.put(new Vector3D(0.51277480689245392, 0.8160427807486631, 0.43547237076648843), new ColorType(165, (byte)0)); // Slime

        types.put(new Vector3D(0.29542850848204449, 0.54230006609385328, 0.4892707644855695), new ColorType(168, (byte)0)); // Prismarine
        types.put(new Vector3D(0.47938785270205647, 0.70707795313247257, 0.64270683883309421), new ColorType(168, (byte)1)); // PrismarineBricks
        types.put(new Vector3D(0.20329909741674448, 0.30687830687830686, 0.25969498910675382), new ColorType(168, (byte)2)); // DarkPrismarine
        types.put(new Vector3D(0.87702519760688236, 0.91413598847111233, 0.87987248351456393), new ColorType(169, (byte)0)); // SeaLantern

        types.put(new Vector3D(0.70893246187363834, 0.58741830065359468, 0.061764705882352944), new ColorType(170, (byte)0)); // HayBale

        types.put(new Vector3D(0.58708898944193055, 0.36006033182503772, 0.25954751131221721), new ColorType(172, (byte)0)); // HardenedClay

        types.put(new Vector3D(0.04665314401622718, 0.04665314401622718, 0.04665314401622718), new ColorType(173, (byte)0)); // CoalBlock

        types.put(new Vector3D(0.66175554333883091, 0.78185816382627815, 0.98834524463991202), new ColorType(174, (byte)0)); // PackedIce

        types.put(new Vector3D(0.64345521992580823, 0.32750397456279812, 0.11478537360890302), new ColorType(179, (byte)0)); // RedSandstone
    }*/
}
