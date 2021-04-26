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
typedef long long ll;
typedef vector<ll> vi64;
typedef vector<vi64> vvi64;
typedef pair<ll, ll> pi64;
typedef double ld;

// const int dx[4] = {1,0,-1,0}, dy[4] = {0,1,0,-1};
// const char move_dict[4] = {'R', 'D', 'L', 'U'};
// const int m,n;
// bool ok(int x, int y) { return x >= 0 && y >= 0 && x < n && y < m; }
map<int, int> prime_factors(ll n) {
    ll f = 2;
    map<int, int> factors;
    while (f*f <= n) {
        if (n % f) {
            if (f==2) {
                f++;
            }
            else {
                f+=2;
            }
            if (f > 500) return map<int, int>();
        }
        else {
            n /= f;
            factors[f] += 1;
        }
    }
    if (n > 1) {
        factors[n] += 1;
    }
    return factors;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    int casen1; cin >> casen1;
    for1(casen, casen1) {
        int m; cin >> m;
        vector<pair<ll, ll>> lst(m);
        map<int,ll>d_cnt;
        ll maxval = 0;
        forn(i,m) {
            cin >> lst[i].fi >> lst[i].se;
            d_cnt[lst[i].fi] = lst[i].se;
            maxval += lst[i].fi * lst[i].se;
        }
        ll ans = 0;
        for (ll i = (ll)(maxval); i >= (ll)(maxval-499*60); --i) {
            map<int, int> d = prime_factors(i);
            if (d.size() > 0) {
                bool ok = true;
                ll s= 0;
                for (const auto& kv : d) {
                    if (d_cnt[kv.fi] > 0) {
                        if (d_cnt[kv.fi] >= kv.se) {
                            s += kv.se * kv.fi;
                        }
                        else {
                            ok=false;
                            break;
                        }
                    }
                    else {ok=false; break;}
                }
                if (!ok) continue;
                if (i == maxval -s) {
                    ans = i;
                    break;
                }
            }
        }
        cout << "Case #" << casen << ": " << ans << "\n";
    }
    return 0;
} 