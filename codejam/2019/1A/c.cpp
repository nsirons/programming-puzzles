#include <bits/stdc++.h>
 
#define mp make_pair
#define mt make_tuple
#define fi first
#define se second
#define pb push_back
#define all(x) (x).begin(), (x).end()
#define rall(x) (x).rbegin(), (x).rend()
#define forn(i, n) for (int i = 0; i < (int)(n); ++i)
#define for1(i, n) for (int i = 1; i <= (int)(n); ++i)
#define ford(i, n) for (int i = (int)(n) - 1; i >= 0; --i)
#define fore(i, a, b) for (int i = (int)(a); i <= (int)(b); ++i)
 
using namespace std;
 
typedef pair<int, int> pii;
typedef vector<int> vi;
typedef vector<pii> vpi;
typedef vector<vi> vvi;
typedef long long i64;
typedef vector<i64> vi64;
typedef vector<vi64> vvi64;
typedef pair<i64, i64> pi64;
typedef double ld;

// const int dx[4] = {1,0,-1,0}, dy[4] = {0,1,0,-1};
// const char move_dict[4] = {'R', 'D', 'L', 'U'};
// const int m,n;
// bool ok(int x, int y) { return x >= 0 && y >= 0 && x < n && y < m; }

struct  Data{
    int val;
    string suffix;
    int a;
    int b;
};

bool acompare(Data lhs, Data rhs) { return lhs.val > rhs.val; }

// solved without trie, in a brute-force manner, 
// apparently this solution is fast enough to pass
int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    int t; cin >> t;
    for1(casen, t) {
        int n; cin >> n;
        vector<string> lst(n);
        forn(i,n) cin >> lst[i];
        vector<Data> match_lst;
        int cnt;
        forn(i,n-1) {
            string word1=lst[i];
            fore(j,i+1,n-1) {
                string word2 = lst[j];
                cnt = 0;
                forn(ii, min(word1.length(), word2.length())) {
                    if (word1[word1.length()-ii-1] != word2[word2.length()-1-ii]) {
                        break;
                    }
                    cnt++;
                }
                if (cnt > 0) {
                    match_lst.pb({cnt, word1.substr(word1.length()-cnt, cnt), i,j});
                }
            }
        }
        set<int> visited;
        set<string> visited_suff;
        sort(all(match_lst), acompare);
        for (Data node: match_lst) {
            if (visited.find(node.a) != visited.end() || visited.find(node.b) != visited.end()) {
                continue;
            }
            int p = 0;
            while (p<node.val && visited_suff.find(node.suffix.substr(p,node.val-p)) != visited_suff.end()) {
                p++;
            }
            if (p==node.val) {continue;}
            visited.insert(node.a);
            visited.insert(node.b);
            visited_suff.insert(node.suffix.substr(p,node.val-p));
        }
        cout << "Case #" << casen << ": " << visited.size() << '\n';
    }
    return 0;
} 