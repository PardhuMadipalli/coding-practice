package problems.backtracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WordSearchII {

    boolean[][] used;
    char[][] board;
    TrieNode rootTrie;
    StringBuilder currTrue;
    String currTrueString;
    Character falseChar;
    int[][] dirAdds = new int[][]{{0, 1}, {0,-1}, {1,0}, {-1,0}};

    private static class TrieNode {
        TrieNode[] children;
        Boolean inBoard;
        public TrieNode() {
            this.children = new TrieNode[26];
        }
        public TrieNode(boolean inBoard) {
            super();
            this.inBoard = inBoard;
        }
    }

    private void updateTrie(String truePart, Character falseChar) {
        TrieNode curr = rootTrie;
        for (int i = 0; i < truePart.length(); i++) {
            char c = truePart.charAt(i);
            int currIndex = c-'a';
            System.out.println("updating " + c);
            if (Objects.isNull(curr.children[currIndex])) {
                curr.children[currIndex] = new TrieNode(true);
            } else if (Objects.isNull(curr.children[currIndex].inBoard)){
                curr.children[currIndex].inBoard = true;
            }
            curr = curr.children[currIndex];
        }

        if (Objects.nonNull(falseChar)) {
            int currIndex = falseChar - 'a';
            if (Objects.isNull(curr.children[currIndex])) {
                curr.children[currIndex] = new TrieNode(false);
            } else {
                curr.children[currIndex].inBoard = false;
            }
        }
    }

    public void visualizeTrie() {
        System.out.println("ROOT");
        visualize(rootTrie, 0);
    }

    private void visualize(TrieNode node, int level) {
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                // Create indentation: 4 spaces per level
                String indent = "    ".repeat(level);
                char c = (char) (i + 'a');

                // Format: |--- character (suffix marker)
                System.out.print(indent + "|--- " + c);
                System.out.print(" " + node.children[i].inBoard);
                System.out.println();

                // Recurse to the next level
                visualize(node.children[i], level + 1);
            }
        }
    }

    public List<String> findWords(char[][] board, String[] words) {

        this.used = new boolean[board.length][board[0].length];
        this.board = board;
        this.rootTrie = new TrieNode();

        List<String> answer = new ArrayList<>();
        for (String word: words) {
//            System.out.println("Finding " + word);
//            printUsed();
            this.currTrue = new StringBuilder();
            this.currTrueString = "";
            this.falseChar = null;
            if (findWord(word.toCharArray())) {
                answer.add(word);
            } else {
                this.falseChar = word.charAt(currTrue.length());
            }
            updateTrie(currTrueString, this.falseChar);
        }
        return answer;
    }

    private boolean findWord(char[] word) {
        int firstLetterIndex = word[0]-'a';

        if (Objects.nonNull(rootTrie.children[firstLetterIndex])) {
            if (Objects.nonNull(rootTrie.children[firstLetterIndex].inBoard) && !rootTrie.children[firstLetterIndex].inBoard) {
                return false;
            }
        } else {
            rootTrie.children[firstLetterIndex] = new TrieNode();
        }

        for(int i = 0; i< board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == word[0]) {
//                    rootTrie.children[firstLetterIndex].inBoard = true;
                    currTrue.append(word[0]);
                    if (currTrue.length() > currTrueString.length()) {
                        currTrueString = currTrue.toString();
                    }
                    if (word.length == 1) return true;
//                    System.out.println("Found first letter: " + word[0] + " at " + i + " and " + j);
                    used[i][j] = true;
                    boolean allLettersMatched = matchRemLetters(word, 1, rootTrie.children[firstLetterIndex], i, j);
                    used[i][j] = false;
                    currTrue.deleteCharAt(currTrue.length()-1);
                    if (allLettersMatched) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean matchRemLetters(char[] word, int wordCurr, TrieNode parentNode, int currBoardI, int currBoardJ) {
        if (wordCurr == word.length) {
            return true;
        }

        int currLetterIndex = word[wordCurr] - 'a';
        System.out.println("trying to find " + word[wordCurr]);
        if (Objects.nonNull(parentNode.children[currLetterIndex])) {
            if (Objects.nonNull(parentNode.children[currLetterIndex].inBoard) && !parentNode.children[currLetterIndex].inBoard) {
                System.out.println("Could not find " + word[wordCurr]);
                visualizeTrie();
                return false;
            }
        } else {
            parentNode.children[currLetterIndex] = new TrieNode();
        }
        for (int[] dir : dirAdds) {
            int newI = currBoardI + dir[0];
            int newJ = currBoardJ + dir[1];
//            System.out.printf("Finding letter %c at %d, %d\n", word[wordCurr], newI, newJ);
            if (newI >= 0 && newJ >= 0 &&  newI < board.length && newJ < board[0].length
                    && !used[newI][newJ]
                    && board[newI][newJ] == word[wordCurr]) {

//                System.out.println("Found letter: " + word[wordCurr] + " at " + newI + " and " + newJ);
                used[newI][newJ] = true;
//                parentNode.children[currLetterIndex].inBoard = true;
                currTrue.append(word[wordCurr]);
                if (currTrue.length() > currTrueString.length()) {
                    currTrueString = currTrue.toString();
                }
                boolean allLettersMatched = matchRemLetters(word, wordCurr + 1, parentNode.children[currLetterIndex], newI, newJ);
                used[newI][newJ] = false;
                currTrue.deleteCharAt(currTrue.length()-1);
                if(allLettersMatched) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{{'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}};
        String[] words = new String[] {"eatp","eat"};
        System.out.println(new WordSearchII().findWords(board, words));
    }
}
