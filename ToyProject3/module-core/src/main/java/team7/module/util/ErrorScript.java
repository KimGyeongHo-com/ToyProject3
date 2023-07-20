package team7.module.util;

public class ErrorScript {

    public static String alertBuild(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append(String.format("alert('%s');", message));
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }
}
