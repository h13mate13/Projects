import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
//what is this commit
public class DecodeSecret {

    public static void decodeAndPrint(String url) {
        try {
            System.out.println("START");
            String text = fetch(url);
            System.out.println("FETCHED " + text.length() + " bytes");

            // Optional preview of first lines
            String[] lines = text.split("\\r?\\n");
            for (int i = 0; i < Math.min(10, lines.length); i++) {
                System.out.println("LINE[" + i + "]: " + lines[i]);
            }

            List<String[]> rows = parseRows(text);
            System.out.println("PARSED triples = " + rows.size());

            List<String> out = render(rows);
            System.out.println("RENDERED lines = " + out.size());

            for (String s : out) System.out.println(s);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static String fetch(String url) throws IOException {
        HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
        c.setConnectTimeout(15000);
        c.setReadTimeout(30000);
        c.setRequestProperty("User-Agent", "Mozilla/5.0");
        try (InputStream in = c.getInputStream()) {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            for (int n; (n = in.read(buf)) >= 0; ) b.write(buf, 0, n);
            String body = b.toString(StandardCharsets.UTF_8);
            String ct = String.valueOf(c.getContentType()).toLowerCase();

            // Coerce simple HTML to plain TSV-like text if needed
            if (ct.contains("text/html") ||
                    body.contains("<html") || body.contains("<table") || body.contains("<tr")) {
                String h = body.replaceAll("(?i)<br\\s*/?>", "\n")
                        .replaceAll("(?is)<(script|style)[^>]*>.*?</\\1>", "")
                        .replaceAll("(?is)</tr\\s*>", "\n")
                        .replaceAll("(?is)</t[dh]\\s*>", "\t")
                        .replaceAll("(?is)<[^>]+>", "")
                        .replaceAll("[ \\t]+", "\t")
                        .trim();
                return h;
            }
            return body;
        }
    }

    // Parse rows specifically in the form: x, Character, y -> stored as [char, x, y]
    private static List<String[]> parseRows(String text) {
        List<String[]> rows = new ArrayList<>();
        String[] lines = text.split("\\R");
        for (String ln : lines) {
            if (ln == null) continue;
            ln = ln.replace('\u00A0', ' ').trim(); // normalize non-breaking spaces
            if (ln.isEmpty()) continue;

            String[] r;
            if (ln.contains("\t")) r = ln.split("\\t");
            else if (ln.contains(",")) r = ln.split(",");
            else if (ln.contains(";")) r = ln.split(";");
            else r = ln.split("\\s+");

            for (int i = 0; i < r.length; i++) r[i] = r[i].trim();
            if (r.length < 3) continue;

            // Expect x in col0, char in col1, y in col2 (as seen in your preview)
            String sx = r[0];
            String ch = r[1];
            String sy = r[2];

            if (!isInt(sx) || !isInt(sy)) {
                // skip prose/header rows
                continue;
            }

            rows.add(new String[]{ ch, sx, sy }); // store as [char, x, y]
        }
        return rows;
    }

    private static boolean isInt(String s) {
        try { Integer.parseInt(s); return true; } catch (Exception e) { return false; }
    }

    private static String normChar(String raw) {
        String s = raw.trim();
        if (s.matches("U\\+[0-9A-Fa-f]{4,6}")) {
            int cp = Integer.parseInt(s.substring(2), 16);
            return new String(Character.toChars(cp));
        }
        if (s.matches("\\\\u[0-9A-Fa-f]{4}")) {
            int cp = Integer.parseInt(s.substring(2), 16);
            return new String(Character.toChars(cp));
        }
        if (s.matches("\\d+") && Integer.parseInt(s) <= 0x10FFFF) {
            int cp = Integer.parseInt(s);
            return new String(Character.toChars(cp));
        }
        return s.isEmpty() ? " " : s.substring(0, 1);
    }

    private static List<String> render(List<String[]> rows) {
        class T { String ch; int x, y; T(String c, int X, int Y){ ch=c; x=X; y=Y; } }
        List<T> ts = new ArrayList<>();
        for (String[] r : rows) {
            try { ts.add(new T(normChar(r[0]), Integer.parseInt(r[1]), Integer.parseInt(r[2]))); }
            catch (Exception ignore) {}
        }
        if (ts.isEmpty()) return List.of();

        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (T t : ts) {
            if (t.x < minX) minX = t.x;
            if (t.y < minY) minY = t.y;
            if (t.x > maxX) maxX = t.x;
            if (t.y > maxY) maxY = t.y;
        } 
        int w = maxX - minX + 1, h = maxY - minY + 1;
        if (w <= 0 || h <= 0) return List.of();

        char[][] g = new char[h][w];
        for (int y = 0; y < h; y++) Arrays.fill(g[y], ' ');
        for (T t : ts) {
            int x = t.x - minX, y = t.y - minY;
            if (0 <= x && x < w && 0 <= y && y < h) g[y][x] = t.ch.charAt(0);
        }
        List<String> out = new ArrayList<>();
        for (int y = 0; y < h; y++) out.add(new String(g[y]));
        return out;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java DecodeSecret <url>");
            return;
        }
        decodeAndPrint(args[0]);
    }
}
