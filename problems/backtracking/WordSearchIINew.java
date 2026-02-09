package problems.backtracking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WordSearchIINew {

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
//            System.out.println("updating " + c);
            if (curr.children == null) {
                curr.children = new TrieNode[26];
            }
            if (Objects.isNull(curr.children[currIndex])) {
                curr.children[currIndex] = new TrieNode(true);
            } else if (Objects.isNull(curr.children[currIndex].inBoard)){
                curr.children[currIndex].inBoard = true;
            }
            curr = curr.children[currIndex];
        }

        if (Objects.nonNull(falseChar)) {
            int currIndex = falseChar - 'a';
            if (curr.children == null) {
                curr.children = new TrieNode[26];
            }
            if (Objects.isNull(curr.children[currIndex])) {
                curr.children[currIndex] = new TrieNode(false);
            } else {
                curr.children[currIndex].inBoard = false;
            }
        }
    }

    private Boolean searchTrie(String word) {
        TrieNode curr = rootTrie;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (curr.children == null || curr.children[index] == null) return null;
            if (!curr.children[index].inBoard) {
                return false;
            }
            curr = curr.children[index];
        }
        return true;
    }

    public void visualizeTrie() {
        System.out.println("ROOT");
        visualize(rootTrie, 0);
    }

    private void visualize(TrieNode node, int level) {
        for (int i = 0; i < 26; i++) {
            if (node.children != null && node.children[i] != null) {
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
            if (findWord(word)) {
                answer.add(word);
            } else {
                this.falseChar = word.charAt(currTrueString.length());
            }
//            visualizeTrie();
            if (!currTrueString.isEmpty()) {
                updateTrie(currTrueString, this.falseChar);
            }
        }
        return answer;
    }

    private boolean findWord(String wordString) {
        Boolean searchWord = searchTrie(wordString);
//        System.out.println("Search word is " + searchWord);
        if (Objects.nonNull(searchWord)) {
            System.out.printf("search word is there already: %s and value: %b\n", wordString, searchWord);
            return searchWord;
        }
        char[] word = wordString.toCharArray();

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
                    boolean allLettersMatched = matchRemLetters(word, 1, i, j);
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

    private boolean matchRemLetters(char[] word, int wordCurr, int currBoardI, int currBoardJ) {
        if (wordCurr == word.length) {
            return true;
        }

//        System.out.println("trying to find " + word[wordCurr]);

//        int currLetterIndex = word[wordCurr] - 'a';
//        if (Objects.nonNull(parentNode.children[currLetterIndex])) {
//            if (Objects.nonNull(parentNode.children[currLetterIndex].inBoard) && !parentNode.children[currLetterIndex].inBoard) {
//                System.out.println("Could not find " + word[wordCurr]);
//                visualizeTrie();
//                return false;
//            }
//        } else {
//            parentNode.children[currLetterIndex] = new TrieNode();
//        }
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
                boolean allLettersMatched = matchRemLetters(word, wordCurr + 1, newI, newJ);
                used[newI][newJ] = false;
                currTrue.deleteCharAt(currTrue.length()-1);
                if(allLettersMatched) return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        char[][] board = new char[][]{{'o','a','a','n'},{'e','t','a','e'},{'i','h','k','r'},{'i','f','l','v'}};
//        String[] words = new String[] {"oaths","oathst","oat", "eat", "rain"};

        char[][] board = new char[][]
                {{'m','b','c','d','e','f','g','h','i','j','k','l'},{'n','a','a','a','a','a','a','a','a','a','a','a'},{'o','a','a','a','a','a','a','a','a','a','a','a'},{'p','a','a','a','a','a','a','a','a','a','a','a'},{'q','a','a','a','a','a','a','a','a','a','a','a'},{'r','a','a','a','a','a','a','a','a','a','a','a'},{'s','a','a','a','a','a','a','a','a','a','a','a'},{'t','a','a','a','a','a','a','a','a','a','a','a'},{'u','a','a','a','a','a','a','a','a','a','a','a'},{'v','a','a','a','a','a','a','a','a','a','a','a'},{'w','a','a','a','a','a','a','a','a','a','a','a'},{'x','y','z','a','a','a','a','a','a','a','a','a'}};
        String[] words = new String[] {"aaaaaaaaaa","aaaaaaaaab","aaaaaaaaac","aaaaaaaaad","aaaaaaaaae","aaaaaaaaaf","aaaaaaaaag","aaaaaaaaah","aaaaaaaaai","aaaaaaaaaj","aaaaaaaaak","aaaaaaaaal","aaaaaaaaam","aaaaaaaaan","aaaaaaaaao","aaaaaaaaap","aaaaaaaaaq","aaaaaaaaar","aaaaaaaaas","aaaaaaaaat","aaaaaaaaau","aaaaaaaaav","aaaaaaaaaw","aaaaaaaaax","aaaaaaaaay","aaaaaaaaaz","aaaaaaaaba","aaaaaaaabb","aaaaaaaabc","aaaaaaaabd","aaaaaaaabe","aaaaaaaabf","aaaaaaaabg","aaaaaaaabh","aaaaaaaabi","aaaaaaaabj","aaaaaaaabk","aaaaaaaabl","aaaaaaaabm","aaaaaaaabn","aaaaaaaabo","aaaaaaaabp","aaaaaaaabq","aaaaaaaabr","aaaaaaaabs","aaaaaaaabt","aaaaaaaabu","aaaaaaaabv","aaaaaaaabw","aaaaaaaabx","aaaaaaaaby","aaaaaaaabz","aaaaaaaaca","aaaaaaaacb","aaaaaaaacc","aaaaaaaacd","aaaaaaaace","aaaaaaaacf","aaaaaaaacg","aaaaaaaach","aaaaaaaaci","aaaaaaaacj","aaaaaaaack","aaaaaaaacl","aaaaaaaacm","aaaaaaaacn","aaaaaaaaco","aaaaaaaacp","aaaaaaaacq","aaaaaaaacr","aaaaaaaacs","aaaaaaaact","aaaaaaaacu","aaaaaaaacv","aaaaaaaacw","aaaaaaaacx","aaaaaaaacy","aaaaaaaacz","aaaaaaaada","aaaaaaaadb","aaaaaaaadc","aaaaaaaadd","aaaaaaaade","aaaaaaaadf","aaaaaaaadg","aaaaaaaadh","aaaaaaaadi","aaaaaaaadj","aaaaaaaadk","aaaaaaaadl","aaaaaaaadm","aaaaaaaadn","aaaaaaaado","aaaaaaaadp","aaaaaaaadq","aaaaaaaadr","aaaaaaaads","aaaaaaaadt","aaaaaaaadu","aaaaaaaadv","aaaaaaaadw","aaaaaaaadx","aaaaaaaady","aaaaaaaadz","aaaaaaaaea","aaaaaaaaeb","aaaaaaaaec","aaaaaaaaed","aaaaaaaaee","aaaaaaaaef","aaaaaaaaeg","aaaaaaaaeh","aaaaaaaaei","aaaaaaaaej","aaaaaaaaek","aaaaaaaael","aaaaaaaaem","aaaaaaaaen","aaaaaaaaeo","aaaaaaaaep","aaaaaaaaeq","aaaaaaaaer","aaaaaaaaes","aaaaaaaaet","aaaaaaaaeu","aaaaaaaaev","aaaaaaaaew","aaaaaaaaex","aaaaaaaaey","aaaaaaaaez","aaaaaaaafa","aaaaaaaafb","aaaaaaaafc","aaaaaaaafd","aaaaaaaafe","aaaaaaaaff","aaaaaaaafg","aaaaaaaafh","aaaaaaaafi","aaaaaaaafj","aaaaaaaafk","aaaaaaaafl","aaaaaaaafm","aaaaaaaafn","aaaaaaaafo","aaaaaaaafp","aaaaaaaafq","aaaaaaaafr","aaaaaaaafs","aaaaaaaaft","aaaaaaaafu","aaaaaaaafv","aaaaaaaafw","aaaaaaaafx","aaaaaaaafy","aaaaaaaafz","aaaaaaaaga","aaaaaaaagb","aaaaaaaagc","aaaaaaaagd","aaaaaaaage","aaaaaaaagf","aaaaaaaagg","aaaaaaaagh","aaaaaaaagi","aaaaaaaagj","aaaaaaaagk","aaaaaaaagl","aaaaaaaagm","aaaaaaaagn","aaaaaaaago","aaaaaaaagp","aaaaaaaagq","aaaaaaaagr","aaaaaaaags","aaaaaaaagt","aaaaaaaagu","aaaaaaaagv","aaaaaaaagw","aaaaaaaagx","aaaaaaaagy","aaaaaaaagz","aaaaaaaaha","aaaaaaaahb","aaaaaaaahc","aaaaaaaahd","aaaaaaaahe","aaaaaaaahf","aaaaaaaahg","aaaaaaaahh","aaaaaaaahi","aaaaaaaahj","aaaaaaaahk","aaaaaaaahl","aaaaaaaahm","aaaaaaaahn","aaaaaaaaho","aaaaaaaahp","aaaaaaaahq","aaaaaaaahr","aaaaaaaahs","aaaaaaaaht","aaaaaaaahu","aaaaaaaahv","aaaaaaaahw","aaaaaaaahx","aaaaaaaahy","aaaaaaaahz","aaaaaaaaia","aaaaaaaaib","aaaaaaaaic","aaaaaaaaid","aaaaaaaaie","aaaaaaaaif","aaaaaaaaig","aaaaaaaaih","aaaaaaaaii","aaaaaaaaij","aaaaaaaaik","aaaaaaaail","aaaaaaaaim","aaaaaaaain","aaaaaaaaio","aaaaaaaaip","aaaaaaaaiq","aaaaaaaair","aaaaaaaais","aaaaaaaait","aaaaaaaaiu","aaaaaaaaiv","aaaaaaaaiw","aaaaaaaaix","aaaaaaaaiy","aaaaaaaaiz","aaaaaaaaja","aaaaaaaajb","aaaaaaaajc","aaaaaaaajd","aaaaaaaaje","aaaaaaaajf","aaaaaaaajg","aaaaaaaajh","aaaaaaaaji","aaaaaaaajj","aaaaaaaajk","aaaaaaaajl","aaaaaaaajm","aaaaaaaajn","aaaaaaaajo","aaaaaaaajp","aaaaaaaajq","aaaaaaaajr","aaaaaaaajs","aaaaaaaajt","aaaaaaaaju","aaaaaaaajv","aaaaaaaajw","aaaaaaaajx","aaaaaaaajy","aaaaaaaajz","aaaaaaaaka","aaaaaaaakb","aaaaaaaakc","aaaaaaaakd","aaaaaaaake","aaaaaaaakf","aaaaaaaakg","aaaaaaaakh","aaaaaaaaki","aaaaaaaakj","aaaaaaaakk","aaaaaaaakl","aaaaaaaakm","aaaaaaaakn","aaaaaaaako","aaaaaaaakp","aaaaaaaakq","aaaaaaaakr","aaaaaaaaks","aaaaaaaakt","aaaaaaaaku","aaaaaaaakv","aaaaaaaakw","aaaaaaaakx","aaaaaaaaky","aaaaaaaakz","aaaaaaaala","aaaaaaaalb","aaaaaaaalc","aaaaaaaald","aaaaaaaale","aaaaaaaalf","aaaaaaaalg","aaaaaaaalh","aaaaaaaali","aaaaaaaalj","aaaaaaaalk","aaaaaaaall","aaaaaaaalm","aaaaaaaaln","aaaaaaaalo","aaaaaaaalp","aaaaaaaalq","aaaaaaaalr","aaaaaaaals","aaaaaaaalt","aaaaaaaalu","aaaaaaaalv","aaaaaaaalw","aaaaaaaalx","aaaaaaaaly","aaaaaaaalz","aaaaaaaama","aaaaaaaamb","aaaaaaaamc","aaaaaaaamd","aaaaaaaame","aaaaaaaamf","aaaaaaaamg","aaaaaaaamh","aaaaaaaami","aaaaaaaamj","aaaaaaaamk","aaaaaaaaml","aaaaaaaamm","aaaaaaaamn","aaaaaaaamo","aaaaaaaamp","aaaaaaaamq","aaaaaaaamr","aaaaaaaams","aaaaaaaamt","aaaaaaaamu","aaaaaaaamv","aaaaaaaamw","aaaaaaaamx","aaaaaaaamy","aaaaaaaamz","aaaaaaaana","aaaaaaaanb","aaaaaaaanc","aaaaaaaand","aaaaaaaane","aaaaaaaanf","aaaaaaaang","aaaaaaaanh","aaaaaaaani","aaaaaaaanj","aaaaaaaank","aaaaaaaanl","aaaaaaaanm","aaaaaaaann","aaaaaaaano","aaaaaaaanp","aaaaaaaanq","aaaaaaaanr","aaaaaaaans","aaaaaaaant","aaaaaaaanu","aaaaaaaanv","aaaaaaaanw","aaaaaaaanx","aaaaaaaany","aaaaaaaanz","aaaaaaaaoa","aaaaaaaaob","aaaaaaaaoc","aaaaaaaaod","aaaaaaaaoe","aaaaaaaaof","aaaaaaaaog","aaaaaaaaoh","aaaaaaaaoi","aaaaaaaaoj","aaaaaaaaok","aaaaaaaaol","aaaaaaaaom","aaaaaaaaon","aaaaaaaaoo","aaaaaaaaop","aaaaaaaaoq","aaaaaaaaor","aaaaaaaaos","aaaaaaaaot","aaaaaaaaou","aaaaaaaaov","aaaaaaaaow","aaaaaaaaox","aaaaaaaaoy","aaaaaaaaoz","aaaaaaaapa","aaaaaaaapb","aaaaaaaapc","aaaaaaaapd","aaaaaaaape","aaaaaaaapf","aaaaaaaapg","aaaaaaaaph","aaaaaaaapi","aaaaaaaapj","aaaaaaaapk","aaaaaaaapl","aaaaaaaapm","aaaaaaaapn","aaaaaaaapo","aaaaaaaapp","aaaaaaaapq","aaaaaaaapr","aaaaaaaaps","aaaaaaaapt","aaaaaaaapu","aaaaaaaapv","aaaaaaaapw","aaaaaaaapx","aaaaaaaapy","aaaaaaaapz","aaaaaaaaqa","aaaaaaaaqb","aaaaaaaaqc","aaaaaaaaqd","aaaaaaaaqe","aaaaaaaaqf","aaaaaaaaqg","aaaaaaaaqh","aaaaaaaaqi","aaaaaaaaqj","aaaaaaaaqk","aaaaaaaaql","aaaaaaaaqm","aaaaaaaaqn","aaaaaaaaqo","aaaaaaaaqp","aaaaaaaaqq","aaaaaaaaqr","aaaaaaaaqs","aaaaaaaaqt","aaaaaaaaqu","aaaaaaaaqv","aaaaaaaaqw","aaaaaaaaqx","aaaaaaaaqy","aaaaaaaaqz","aaaaaaaara","aaaaaaaarb","aaaaaaaarc","aaaaaaaard","aaaaaaaare","aaaaaaaarf","aaaaaaaarg","aaaaaaaarh","aaaaaaaari","aaaaaaaarj","aaaaaaaark","aaaaaaaarl","aaaaaaaarm","aaaaaaaarn","aaaaaaaaro","aaaaaaaarp","aaaaaaaarq","aaaaaaaarr","aaaaaaaars","aaaaaaaart","aaaaaaaaru","aaaaaaaarv","aaaaaaaarw","aaaaaaaarx","aaaaaaaary","aaaaaaaarz","aaaaaaaasa","aaaaaaaasb","aaaaaaaasc","aaaaaaaasd","aaaaaaaase","aaaaaaaasf","aaaaaaaasg","aaaaaaaash","aaaaaaaasi","aaaaaaaasj","aaaaaaaask","aaaaaaaasl","aaaaaaaasm","aaaaaaaasn","aaaaaaaaso","aaaaaaaasp","aaaaaaaasq","aaaaaaaasr","aaaaaaaass","aaaaaaaast","aaaaaaaasu","aaaaaaaasv","aaaaaaaasw","aaaaaaaasx","aaaaaaaasy","aaaaaaaasz","aaaaaaaata","aaaaaaaatb","aaaaaaaatc","aaaaaaaatd","aaaaaaaate","aaaaaaaatf","aaaaaaaatg","aaaaaaaath","aaaaaaaati","aaaaaaaatj","aaaaaaaatk","aaaaaaaatl","aaaaaaaatm","aaaaaaaatn","aaaaaaaato","aaaaaaaatp","aaaaaaaatq","aaaaaaaatr","aaaaaaaats","aaaaaaaatt","aaaaaaaatu","aaaaaaaatv","aaaaaaaatw","aaaaaaaatx","aaaaaaaaty","aaaaaaaatz","aaaaaaaaua","aaaaaaaaub","aaaaaaaauc","aaaaaaaaud","aaaaaaaaue","aaaaaaaauf","aaaaaaaaug","aaaaaaaauh","aaaaaaaaui","aaaaaaaauj","aaaaaaaauk","aaaaaaaaul","aaaaaaaaum","aaaaaaaaun","aaaaaaaauo","aaaaaaaaup","aaaaaaaauq","aaaaaaaaur","aaaaaaaaus","aaaaaaaaut","aaaaaaaauu","aaaaaaaauv","aaaaaaaauw","aaaaaaaaux","aaaaaaaauy","aaaaaaaauz","aaaaaaaava","aaaaaaaavb","aaaaaaaavc","aaaaaaaavd","aaaaaaaave","aaaaaaaavf","aaaaaaaavg","aaaaaaaavh","aaaaaaaavi","aaaaaaaavj","aaaaaaaavk","aaaaaaaavl","aaaaaaaavm","aaaaaaaavn","aaaaaaaavo","aaaaaaaavp","aaaaaaaavq","aaaaaaaavr","aaaaaaaavs","aaaaaaaavt","aaaaaaaavu","aaaaaaaavv","aaaaaaaavw","aaaaaaaavx","aaaaaaaavy","aaaaaaaavz","aaaaaaaawa","aaaaaaaawb","aaaaaaaawc","aaaaaaaawd","aaaaaaaawe","aaaaaaaawf","aaaaaaaawg","aaaaaaaawh","aaaaaaaawi","aaaaaaaawj","aaaaaaaawk","aaaaaaaawl","aaaaaaaawm","aaaaaaaawn","aaaaaaaawo","aaaaaaaawp","aaaaaaaawq","aaaaaaaawr","aaaaaaaaws","aaaaaaaawt","aaaaaaaawu","aaaaaaaawv","aaaaaaaaww","aaaaaaaawx","aaaaaaaawy","aaaaaaaawz","aaaaaaaaxa","aaaaaaaaxb","aaaaaaaaxc","aaaaaaaaxd","aaaaaaaaxe","aaaaaaaaxf","aaaaaaaaxg","aaaaaaaaxh","aaaaaaaaxi","aaaaaaaaxj","aaaaaaaaxk","aaaaaaaaxl","aaaaaaaaxm","aaaaaaaaxn","aaaaaaaaxo","aaaaaaaaxp","aaaaaaaaxq","aaaaaaaaxr","aaaaaaaaxs","aaaaaaaaxt","aaaaaaaaxu","aaaaaaaaxv","aaaaaaaaxw","aaaaaaaaxx","aaaaaaaaxy","aaaaaaaaxz","aaaaaaaaya","aaaaaaaayb","aaaaaaaayc","aaaaaaaayd","aaaaaaaaye","aaaaaaaayf","aaaaaaaayg","aaaaaaaayh","aaaaaaaayi","aaaaaaaayj","aaaaaaaayk","aaaaaaaayl","aaaaaaaaym","aaaaaaaayn","aaaaaaaayo","aaaaaaaayp","aaaaaaaayq","aaaaaaaayr","aaaaaaaays","aaaaaaaayt","aaaaaaaayu","aaaaaaaayv","aaaaaaaayw","aaaaaaaayx","aaaaaaaayy","aaaaaaaayz","aaaaaaaaza","aaaaaaaazb","aaaaaaaazc","aaaaaaaazd","aaaaaaaaze","aaaaaaaazf","aaaaaaaazg","aaaaaaaazh","aaaaaaaazi","aaaaaaaazj","aaaaaaaazk","aaaaaaaazl","aaaaaaaazm","aaaaaaaazn","aaaaaaaazo","aaaaaaaazp","aaaaaaaazq","aaaaaaaazr","aaaaaaaazs","aaaaaaaazt","aaaaaaaazu","aaaaaaaazv","aaaaaaaazw","aaaaaaaazx","aaaaaaaazy","aaaaaaaazz"};

        System.out.println("words input length is " + words.length);
        var output = new WordSearchIINew().findWords(board, words);
        System.out.println(output);
    }
}
