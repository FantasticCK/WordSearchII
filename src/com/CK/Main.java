package com.CK;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        char[] a = {'o', 'a', 'a', 'n'};
        char[] b = {'e', 't', 'a', 'e'};
        char[] c = {'i', 'h', 'k', 'r'};
        char[] d = {'i', 'f', 'l', 'v'};

        char[][] board = {a, b, c, d};
        Solution2 solution = new Solution2();
        String[] words = {"oath", "pea", "eat", "rain", "oath"};
        System.out.println(solution.findWords(board, words));
    }
}

class Solution {
    private List<String> res;

    public List<String> findWords(char[][] board, String[] words) {
        res = new ArrayList<>();
        if (board == null || board.length == 0 || board[0].length == 0) return res;
        int row = board.length;
        int col = board[0].length;

        for (String word : words) {
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if(dfs(board, word, r, c, 0) && !res.contains(word)) res.add(word);
                }
            }
        }
        return res;
    }

    private boolean dfs(char[][] board, String word, int r, int c, int index) {
        int row = board.length;
        int col = board[0].length;
        if (index >= word.length()) return true;
        if (r >= row || r < 0 || c >= col || c < 0) return false;
        if (!(word.charAt(index) == board[r][c])) return false;
        char tmp = board[r][c];
        board[r][c] = '*';
        boolean res = dfs(board, word, r + 1, c, index + 1) ||
                dfs(board, word, r, c + 1, index + 1) ||
                dfs(board, word, r - 1, c, index + 1) ||
                dfs(board, word, r, c - 1, index + 1);
        board[r][c] = tmp;
        return res;
    }
}

// Using Trie data structure
class Solution2 {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        TrieNode root = buildTrie(words);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs (board, i, j, root, res);
            }
        }
        return res;
    }

    public void dfs(char[][] board, int i, int j, TrieNode p, List<String> res) {
        char c = board[i][j];
        if (c == '#' || p.next[c - 'a'] == null) return;
        p = p.next[c - 'a'];
        if (p.word != null) {   // found one
            res.add(p.word);
            p.word = null;     // de-duplicate
        }

        board[i][j] = '#';
        if (i > 0) dfs(board, i - 1, j ,p, res);
        if (j > 0) dfs(board, i, j - 1, p, res);
        if (i < board.length - 1) dfs(board, i + 1, j, p, res);
        if (j < board[0].length - 1) dfs(board, i, j + 1, p, res);
        board[i][j] = c;
    }

    public TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String w : words) {
            TrieNode p = root;
            for (char c : w.toCharArray()) {
                int i = c - 'a';
                if (p.next[i] == null) p.next[i] = new TrieNode();
                p = p.next[i];
            }
            p.word = w;
        }
        return root;
    }

    class TrieNode {
        TrieNode[] next = new TrieNode[26];
        String word;
    }
}