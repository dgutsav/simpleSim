import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class KnockoutTree {
    class TreeNode {
        private int match_id;
        private List<TreeNode> children;
        private TreeNode parent;

        public TreeNode() {
            this.match_id = -1;
            this.parent = null;
            this.children = new ArrayList<>();
        }

        public TreeNode(int match_id, TreeNode parent) {
            this.match_id = match_id;
            this.parent = parent;
            this.children = new ArrayList<>();
        }

        public int getMatchId() {
            return this.match_id;
        }

        public TreeNode getParent() {
            return this.parent;
        }

        public List<TreeNode> getChildren() {
            return this.children;
        }

        public void setMatchId(int id) {
            this.match_id = id;
        }

        public void setParent(TreeNode parent) {
            this.parent = parent;
        }

        public void setChildren(List<TreeNode> children) {
            this.children = children;
        }

        public void addChild(TreeNode child) {
            this.children.add(child);
        }
    }

    private Map<Integer, KnockoutTie> ties = new HashMap<>();
    private String[][] stages;
    private int size;

    private TreeNode finalMatch;

    public KnockoutTree(String[][] stages, int size) {
        this.stages = stages;
        this.size = size;
    }

    private void init() {
        finalMatch = new TreeNode();
        int match_count = 0;
        finalMatch.setMatchId(match_count);
        for (int i = 1; i< size; i++) {
            for (int x = 0; x < Math.pow(2, i); x++) {
                
            }
        }
    }
}