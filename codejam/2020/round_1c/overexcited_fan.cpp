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


int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    int t; cin >> t;
    int ans;
    int x,y;string s;
    bool ok; 
    for1(casen,t) {
        ok = false;
        cin >> x >> y >> s;
        forn(i,s.size()) {
            if (s[i] == 'N') y++;
            else if (s[i] == 'S') y--;
            else if (s[i] == 'E') x++;
            else if (s[i] == 'W') x--;
            if (abs(x) + abs(y) <= i + 1){
                ans = i + 1; 
                ok = true; 
                break; 
            }
        }
        if (ok) cout << "Case #" << casen << ": " << ans << '\n';
        else cout << "Case #" << casen << ": IMPOSSIBLE\n";
    }
    return 0;
} 