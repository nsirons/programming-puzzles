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

vector<pair<int,pair<int, i64> > > graph[10000];
set<int> visited;

i64 gcd(i64 a, i64 b)
{
    if (b == 0)
        return a;
    return gcd(b, a % b);
     
}

i64 dfs(int node, int w, i64 ai) {
    if (visited.find(node) != visited.end()) return -2;
    if (node == 0) {return ai;}
    visited.insert(node);
    for (pair<int,pair<int, i64> > next_node : graph[node]) {
        // cerr << "from " << node <<"->" << next_node.fi << "  " << next_node.se.fi << "  " << w << '\n';
        if (next_node.se.fi <= w) {
            if (ai == -1) {
                ai = next_node.se.se;
            }
            else {
                ai = gcd(ai, next_node.se.se);
            }
            
        }
        i64 ans = dfs(next_node.fi, w, ai);
        if (ans != -2) {
            return ans;
        }
        
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    int casen1; cin >> casen1;
    int x,y,l,c,w;
    i64 a;
    for1(casen, casen1) {
        int n,q; cin >> n >> q;
        forn(i,n) {
            graph[i].clear();
        }
        forn(i,n-1) {
            cin >> x >> y >> l >> a;
            graph[x-1].pb(mp(y-1,mp(l,a)));
            graph[y-1].pb(mp(x-1,mp(l,a)));
        }
        cout << "Case #"<<casen << ": ";
        forn(qq,q) {
            cin >> c >> w;
            visited.clear();
            i64 ans = dfs(c-1,w, -1);
            if (ans == -1) {cout << 0;}
            else {cout << ans;}
            cout << ' ';
        }
        cout << '\n';
    }
    return 0;
} 