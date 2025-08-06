import java.util.*;

public class RecursiveTreePreview {

    // =========================
    // 1. 遞迴計算資料夾的總檔案數（模擬檔案系統）
    // =========================
    // 抽象節點
    static abstract class FSNode {
        String name;
        FSNode(String name) { this.name = name; }
    }
    // 檔案節點
    static class FileNode extends FSNode {
        FileNode(String name) { super(name); }
    }
    // 資料夾節點
    static class DirectoryNode extends FSNode {
        List<FSNode> children = new ArrayList<>();
        DirectoryNode(String name) { super(name); }
        void add(FSNode node) { children.add(node); }
    }

    /** 遞迴計算給定資料夾內的所有檔案數 */
    public static int countFiles(DirectoryNode dir) {
        int count = 0;
        for (FSNode node : dir.children) {
            if (node instanceof FileNode) {
                count++;
            } else if (node instanceof DirectoryNode) {
                count += countFiles((DirectoryNode) node);
            }
        }
        return count;
    }

    /** 遞迴列印資料夾結構（附縮排） */
    public static void printDirectory(DirectoryNode dir, int level) {
        // 縮排
        String indent = "  ".repeat(level);
        System.out.println(indent + "[Dir] " + dir.name);
        for (FSNode node : dir.children) {
            if (node instanceof FileNode) {
                System.out.println(indent + "  [File] " + node.name);
            } else {
                printDirectory((DirectoryNode) node, level + 1);
            }
        }
    }


    // =========================
    // 2. 遞迴列印多層選單結構
    // =========================
    static class MenuItem {
        String title;
        List<MenuItem> subItems = new ArrayList<>();
        MenuItem(String title) { this.title = title; }
        void addSub(MenuItem mi) { subItems.add(mi); }
    }

    /** 遞迴印出選單並標示層級 */
    public static void printMenu(MenuItem item, int level) {
        String indent = "  ".repeat(level);
        System.out.println(indent + "- " + item.title);
        for (MenuItem child : item.subItems) {
            printMenu(child, level + 1);
        }
    }


    // =========================
    // 3. 遞迴處理巢狀陣列的展平
    // =========================
    /** 將巢狀 Object[] 中的 Integer 展平成 List<Integer> */
    public static List<Integer> flattenArray(Object[] arr) {
        List<Integer> result = new ArrayList<>();
        flattenHelper(arr, result);
        return result;
    }
    private static void flattenHelper(Object[] arr, List<Integer> res) {
        for (Object o : arr) {
            if (o instanceof Integer) {
                res.add((Integer) o);
            } else if (o instanceof Object[]) {
                flattenHelper((Object[]) o, res);
            }
        }
    }


    // =========================
    // 4. 遞迴計算巢狀清單的最大深度
    // =========================
    /** 巢狀 Object[] 的最大深度計算 */
    public static int maxDepth(Object[] arr) {
        return maxDepthHelper(arr, 1);
    }
    private static int maxDepthHelper(Object[] arr, int currentDepth) {
        int max = currentDepth;
        for (Object o : arr) {
            if (o instanceof Object[]) {
                int childDepth = maxDepthHelper((Object[]) o, currentDepth + 1);
                if (childDepth > max) {
                    max = childDepth;
                }
            }
        }
        return max;
    }


    // =========================
    // 主程式：建立範例結構並測試上述功能
    // =========================
    public static void main(String[] args) {
        System.out.println("=== 1. 模擬檔案系統遞迴計算與列印 ===");
        DirectoryNode root = new DirectoryNode("root");
        root.add(new FileNode("file1.txt"));
        root.add(new FileNode("file2.log"));
        DirectoryNode subA = new DirectoryNode("subA");
        subA.add(new FileNode("a1.jpg"));
        DirectoryNode subA1 = new DirectoryNode("subA1");
        subA1.add(new FileNode("a1a.txt"));
        subA1.add(new FileNode("a1b.txt"));
        subA.add(subA1);
        root.add(subA);
        System.out.println("Directory structure:");
        printDirectory(root, 0);
        System.out.println("Total files: " + countFiles(root));
        System.out.println();

        System.out.println("=== 2. 多層選單遞迴列印 ===");
        MenuItem menu = new MenuItem("File");
        MenuItem mNew = new MenuItem("New");
        mNew.addSub(new MenuItem("Project"));
        mNew.addSub(new MenuItem("File"));
        menu.addSub(mNew);
        menu.addSub(new MenuItem("Open"));
        MenuItem mSave = new MenuItem("Save");
        mSave.addSub(new MenuItem("Save As"));
        menu.addSub(mSave);
        System.out.println("Menu:");
        printMenu(menu, 0);
        System.out.println();

        System.out.println("=== 3. 巢狀陣列遞迴展平 ===");
        Object[] nestedArray = {
            1,
            new Object[]{2, 3, new Object[]{4, 5}},
            6,
            new Object[]{7, new Object[]{8, new Object[]{9}}, 10}
        };
        System.out.println("Nested array: " + Arrays.deepToString(nestedArray));
        List<Integer> flat = flattenArray(nestedArray);
        System.out.println("Flattened: " + flat);
        System.out.println();

        System.out.println("=== 4. 巢狀清單最大深度計算 ===");
        Object[] nestedList = {
            1,
            new Object[]{2, new Object[]{3, new Object[]{4}}, 5},
            6
        };
        System.out.println("Nested list: " + Arrays.deepToString(nestedList));
        System.out.println("Max depth: " + maxDepth(nestedList));
    }
}
