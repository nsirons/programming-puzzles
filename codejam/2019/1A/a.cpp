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


int h,w;
vpi path;
bool maze[20][20];
void dfs(int x, int y) {
    if (path.size() == h*w) {return;}

    if (!maze[x][y]) {
        maze[x][y] = true;
        vpi opt;
        forn(xx, h){
            if (xx == x) {continue;}
            forn(yy, w) {
                if (yy == y) {continue;}
                if ((x - y == xx - yy) || (x + y == xx + yy)) {continue;}
                if (maze[xx][yy]) {continue;}
                opt.pb({xx,yy});
            }
        }
        if (opt.size() > 0) {
            pii node = opt[rand() % opt.size()];
            path.pb({node.fi,node.se});
            dfs(node.fi, node.se);
            if (path.size() == h*w) {return;}
            path.pop_back();
            maze[node.fi][node.se] = false;
        }
    }
}

// Solved A-Small with just dfs backtracking
// A-Big used hint to generate random paths
int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    int t; cin >> t;
    for1(casen, t) {
        cin >> h >> w;
        forn(i,h) forn(j,w) maze[i][j] = false;
        forn(asd,1000000) {
            int ii = rand() % h;
            int jj = rand() % w;
            path.clear();
            path.pb({ii,jj});
            dfs(ii,jj);
            maze[ii][jj] = false;
            if (path.size() == h*w) {break;}
        } 
        cout << "Case #" << casen << ": ";
        if (path.size() == h*w) {
            cout << "POSSIBLE\n";
            for(pii p: path) {
                cout << p.fi+1 <<  ' ' << p.se + 1<< '\n';
            }
        }
        else {
            cout << "IMPOSSIBLE\n";
        }  
    }
    return 0;
}