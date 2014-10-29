package es.flakiness.bffl;

import java.util.Arrays;
import java.util.List;

public class Picture {
    Long _id;
    int status;
    String page;
    /** 75 */
    String squareImage;
    /** 240-320 */
    String smallImage;
    /** 600 - 800 */
    String mediumImage;
    /** 1000 - */
    String largeImage;

    public Picture() {

    }

    public Picture(int status, String page, String squareImage, String smallImage, String mediumImage, String largeImage) {
        this.status = status;
        this.page = page;
        this.squareImage = squareImage;
        this.smallImage = smallImage;
        this.mediumImage = mediumImage;
        this.largeImage = largeImage;
    }

    public static List<Picture> createBuiltinList() {
        return Arrays.asList(new Picture[] {
                new Picture(
                        BuildStatus.PASSED.sequence(),
                        "https://flic.kr/p/evCGUY",
                        "https://farm4.staticflickr.com/3787/8867978032_10cf2b74a8_s.jpg",
                        "https://farm4.staticflickr.com/3787/8867978032_10cf2b74a8_n.jpg",
                        "https://farm4.staticflickr.com/3787/8867978032_10cf2b74a8_c.jpg",
                        "https://farm4.staticflickr.com/3787/8867978032_e3563f5ce8_h.jpg"),
                new Picture(
                        BuildStatus.PASSED.sequence(),
                        "https://flic.kr/p/HPRAC",
                        "https://farm1.staticflickr.com/222/473313444_c1a20abee2_s.jpg",
                        "https://farm1.staticflickr.com/222/473313444_c1a20abee2_m.jpg",
                        "https://farm1.staticflickr.com/222/473313444_76d14c290b_o.jpg",
                        ""),
                new Picture(
                        BuildStatus.PASSED.sequence(),
                        "https://flic.kr/p/EYKcs",
                        "https://farm1.staticflickr.com/181/441098436_81b708e0f5_s.jpg",
                        "https://farm1.staticflickr.com/181/441098436_81b708e0f5_n.jpg",
                        "https://farm1.staticflickr.com/181/441098436_81b708e0f5_b.jpg",
                        ""),
                new Picture(
                        BuildStatus.FAILED.sequence(),
                        "https://flic.kr/p/5XvTtk",
                        "https://farm4.staticflickr.com/3489/3253665749_0b7225a419_s.jpg",
                        "https://farm4.staticflickr.com/3489/3253665749_0b7225a419_n.jpg",
                        "https://farm4.staticflickr.com/3489/3253665749_0b7225a419_z.jpg",
                        "https://farm4.staticflickr.com/3489/3253665749_0b7225a419_b.jpg"),
                new Picture(
                        BuildStatus.FAILED.sequence(),
                        "https://flic.kr/p/8zkWGd",
                        "https://farm5.staticflickr.com/4103/4971832860_8c25345e37_s.jpg",
                        "https://farm5.staticflickr.com/4103/4971832860_8c25345e37_n.jpg",
                        "https://farm5.staticflickr.com/4103/4971832860_8c25345e37_z.jpg",
                        "https://farm5.staticflickr.com/4103/4971832860_8c25345e37_b.jpg"),
                new Picture(
                        BuildStatus.FAILED.sequence(),
                        "https://flic.kr/p/pEqvbX",
                        "https://farm4.staticflickr.com/3929/15531013391_0348fcea1a_s.jpg",
                        "https://farm4.staticflickr.com/3929/15531013391_0348fcea1a_n.jpg",
                        "https://farm4.staticflickr.com/3929/15531013391_0348fcea1a_c.jpg",
                        "https://farm4.staticflickr.com/3929/15531013391_9dfc2238fa_h.jpg"),
        });
    }
}
