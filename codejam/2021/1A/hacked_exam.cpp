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
typedef  unsigned long long ull;

// const int dx[4] = {1,0,-1,0}, dy[4] = {0,1,0,-1};
// const char move_dict[4] = {'R', 'D', 'L', 'U'};
// const int m,n;
// bool ok(int x, int y) { return x >= 0 && y >= 0 && x < n && y < m; }

int Q;
__int128 memo[41][41][41];
bool quest[2][40];
const __int128 maxval = -1;

std::ostream&
operator<<( std::ostream& dest, __int128_t value )
{
    std::ostream::sentry s( dest );
    if ( s ) {
        __uint128_t tmp = value < 0 ? -value : value;
        char buffer[ 128 ];
        char* d = std::end( buffer );
        do
        {
            -- d;
            *d = "0123456789"[ tmp % 10 ];
            tmp /= 10;
        } while ( tmp != 0 );
        if ( value < 0 ) {
            -- d;
            *d = '-';
        }
        int len = std::end( buffer ) - d;
        if ( dest.rdbuf()->sputn( d, len ) != len ) {
            dest.setstate( std::ios_base::badbit );
        }
    }
    return dest;
}

__int128 f(int s1,int s2,int q) {
    if ((s1 < 0) || (s2 < 0)) {
        return 0;
    }
    if (s1 == 0 && s2 == 0 && q == Q) {
        return (__int128)1;
    }
    if (q == Q){
        return (__int128)0;
    } 
    __int128 a,b;
    if (s1 - quest[0][q] >= 0 && s2 - quest[1][q] >= 0) {

        if (memo[s1 - quest[0][q]][s2-quest[1][q]][q+1] == maxval) {
            a = f(s1 - quest[0][q], s2-quest[1][q], q+1);
        }
        else{
            a = memo[s1 - quest[0][q]][s2-quest[1][q]][q+1];
        }
    }
    else {a=0;}
    if (s1 - (1-quest[0][q]) >= 0 && s2-(1-quest[1][q]) >= 0) {
        if (memo[s1 - (1-quest[0][q])][s2-(1-quest[1][q])][q+1] == maxval) {
            b = f(s1 - (1-quest[0][q]), s2-(1-quest[1][q]), q+1);
        }
        else {
            b = memo[s1 - (1-quest[0][q])][s2-(1-quest[1][q])][q+1];
        }
    }
    else {
        b = 0;
    }
    memo[s1][s2][q] = a+b;
    return a+b;
};


__int128 gcd(__int128 a , __int128 b)
{
   if(b==0) return a;
   a%=b;
   return gcd(b,a);
}

__int128 lcm(__int128 a, __int128 b) {
    return a/gcd(a,b)*b;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    int casen1; cin >> casen1;
    for1(casen, casen1) {
        int n; cin >> n >> Q;
        vi score(n);
        string c;
        forn(i,n) {
            cin >> c >> score[i];
            forn(j,Q) {
                quest[i][j] = (bool)(c[j] == 'T');
            }
        }
        string anss = "";
        __int128 all_opt;
        __int128 ans;
        if (n==1 || n == 3) {
            if (score[0] >= Q-score[0]) {
                forn(i,Q) {
                    anss += (quest[0][i]? "T": "F");
                }
            }
            else {
                forn(i,Q) {
                    anss += (quest[0][i]? "F" : "T");
                }
            }
            ans = max((__int128)score[0], (__int128)Q-score[0]);
            all_opt = (__int128)1;
        }
        else if (n==2) {
            forn(i,Q+1) {forn(j,Q+1) {forn(k,Q+1) {memo[i][j][k] = maxval;}}}
            double s = 0;

            vector<__int128> num(Q,0), denom(Q,1);
            __int128 common_denom = 1;
            forn(i, Q){             
                ans = f(score[0]-quest[0][0], score[1]-quest[1][0], 1);
                all_opt = f(score[0], score[1], 0);

                
                common_denom = lcm(common_denom, all_opt);
                denom[i] = all_opt;
                if (ans >= all_opt-ans) {
                    anss += "T";
                }
                else {
                    anss += "F";
                }
                num[i] = max(ans, all_opt-ans);
                forn(jj,n) {
                    bool temp = quest[jj][Q-1];
                    ford(j,Q) {
                        quest[jj][j+1] = quest[jj][j];
                    }
                    quest[jj][0] = temp;
                }
            }
             all_opt = common_denom;
                ans = 0;
                forn(i,Q) {
                    ans += common_denom/denom[i]*num[i];

                }
                __int128 g = gcd(ans, all_opt);
                ans /= g;
                all_opt /= g;
            
        }
        cout << "Case #" << casen << ": " << anss << " " << ans <<"/" << all_opt << "\n";
    }
    return 0;
} 