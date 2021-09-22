public class Emoji {

    public static String emoji;
    final public static String coffee = "☕";
    final public static String star = "☆";
    //final public static String watch = "\u231A";
    final public static String faceMask = "\uD83D\uDE37";
    final public static String virus = "\uD83D\uDC79";
    //final public static String droplet = "\uD83D\uDCA7";

    public Emoji() {
    }

    public static String getEmoji() {
        return emoji;
    }

    public static void setEmoji(String emoji) {
        Emoji.emoji = emoji;
    }

    public static String getCoffee() {
        return coffee;
    }

    public static String getStar() {
        return star;
    }

    public static String getFaceMask() {
        return faceMask;
    }

    public static String getVirus() {
        return virus;
    }

}
