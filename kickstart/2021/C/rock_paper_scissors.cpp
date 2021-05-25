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
const int MAXN=61;
pair<double, char> dp[MAXN][MAXN][MAXN];


int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    int t,x; cin>>t >> x;
    forn(casen, t) {
        double max_score = 0.0;
        string ans = "";
        double w,e; cin >> w >> e;
        forn(ri,MAXN) forn(si,MAXN) forn(pi, MAXN) {
            dp[ri][si][pi].fi = 0.0;
            dp[ri][si][pi].se = '.';
        }
         
        double add_ri, add_si,add_pi;
        dp[0][0][1].fi = (w+e) / 3.;
        dp[0][0][1].se = 'P';
        dp[0][1][0].fi = (w+e) / 3.;
        dp[0][1][0].se = 'S';
        dp[1][0][0].fi = (w+e) / 3.;
        dp[1][0][0].se = 'R';
        
        forn(ri,MAXN){
            forn(si, MAXN) {
                forn (pi, MAXN) {
                    if (ri+pi+si == 0) continue;
                    add_ri = (w*pi + e*si) / double(ri+pi+si);
                    add_si = (w*ri + e*pi) / double(ri+pi+si);
                    add_pi = (w*si + e*ri) / double(ri+pi+si);
                    if (ri-1 >= 0 && dp[ri][si][pi].fi < dp[ri-1][si][pi].fi + add_ri){
                         dp[ri][si][pi].fi = dp[ri-1][si][pi].fi + add_ri;
                         dp[ri][si][pi].se = 'R';
                    }
                    if (si-1 >= 0 && dp[ri][si][pi].fi < dp[ri][si-1][pi].fi + add_si){
                         dp[ri][si][pi].fi = dp[ri][si-1][pi].fi + add_si;
                         dp[ri][si][pi].se = 'S';
                    }
                    if (pi-1 >= 0 && dp[ri][si][pi].fi < dp[ri][si][pi-1].fi + add_pi){
                         dp[ri][si][pi].fi = dp[ri][si][pi-1].fi + add_pi;
                         dp[ri][si][pi].se = 'P';
                    }
                }
            }
        }
        pair<int, pii> start_from;
        forn(ri,MAXN){
            forn(si, MAXN) {
                forn (pi, MAXN) {
                    if (ri+si+pi == 60) {
                        if (dp[ri][si][pi].fi > max_score) {
                            max_score = dp[ri][si][pi].fi;
                            start_from.fi = ri;
                            start_from.se.fi = si;
                            start_from.se.se = pi;
                        }
                    }
                }
            }
        }
        forn(i,60) {
            // if (start_from.fi <0 || start_from.se.fi < 0 || start_from.se.se < 0) {
            //     cerr << "NOOO";
            //     break;
            // }
            char last_move = dp[start_from.fi][start_from.se.fi][start_from.se.se].se;
            ans += last_move;
            if (last_move == 'R') {
                start_from.fi -= 1;
            }
            else if (last_move == 'S') {
                start_from.se.fi -= 1;
            }
            else {
                start_from.se.se -= 1;
            }
        }
        reverse(all(ans));

        // cerr << "MAX SCORE " << max_score << '\n';
        cout << "Case #" << casen+1 << ": " << ans << '\n';
    }
    return 0;
}
