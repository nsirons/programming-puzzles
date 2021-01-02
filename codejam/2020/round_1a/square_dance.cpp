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


struct Node {
    int val;
    Node *left;
    Node *right;
    Node *up;
    Node *down;
    int id;
};

Node *dance_node[100005];

int main() {
    ios::sync_with_stdio(false);
    cin.tie(0);
    int t; cin >> t;

    Node *empty_node = new Node();
    empty_node->val = 0;
    forn(i, 100005) dance_node[i] = new Node();

    for1(casen, t) {
        i64 ans=0;
        int r,c; cin >> r >> c;
        i64 round_sum = 0;
        set<int> node_set;
        
        forn(i,r) { 
            forn(j,c) {
                cin >> dance_node[i*c+j] -> val; 
                round_sum += dance_node[i*c+j] -> val;
                dance_node[i*c+j] -> id = i*c+j;
            }
        }

        forn(i,r) {
            forn(j, c) {
                if (i != 0) dance_node[i*c+j]->up = dance_node[(i-1)*c+j];
                else dance_node[i*c+j]->up=empty_node;
                if (i != r-1) dance_node[i*c+j]->down = dance_node[(i+1)*c+j];
                else dance_node[i*c+j]->down=empty_node;
                if (j != 0) dance_node[i*c+j]->left = dance_node[i*c+j-1];
                else dance_node[i*c+j]->left=empty_node;
                if (j != c-1) dance_node[i*c+j]->right = dance_node[i*c+j+1];
                else dance_node[i*c+j]->right=empty_node;

                node_set.insert(i*c+j);
            }
        }

        while (!node_set.empty()) {
            ans += round_sum;
            vi node_remove_arr;
            for (int active_node: node_set) {
                int node_val = dance_node[active_node]->val;

                int left_val = dance_node[active_node]->left->val;
                int right_val = dance_node[active_node]->right->val;
                int up_val = dance_node[active_node]->up->val;
                int down_val = dance_node[active_node]->down->val;

                int cnt = 0;
                int num = 0;
                if (left_val == 0) cnt++; num += left_val;
                if (right_val == 0) cnt++; num += right_val;
                if (up_val == 0) cnt++; num += up_val;
                if (down_val == 0) cnt++; num += down_val;

                if (cnt != 4) {
                    double average = num / (4.0 - cnt);
                    if (average > node_val) node_remove_arr.pb(active_node);
                }
            }

            if (node_remove_arr.empty()) break;
            
            
            for (int node: node_remove_arr) {
                round_sum -= dance_node[node]->val;
                dance_node[node]->val = 0;
                dance_node[node]->right->left = dance_node[node]->left;
                dance_node[node]->left->right = dance_node[node]->right; 
                dance_node[node]->up->down = dance_node[node]->down; 
                dance_node[node]->down->up = dance_node[node]->up;
            }
            node_set.clear();
            for (int node: node_remove_arr) {
                if(dance_node[node]->right->val !=0) node_set.insert(dance_node[node]->right->id);
                if(dance_node[node]->left->val !=0) node_set.insert(dance_node[node]->left->id);
                if(dance_node[node]->up->val !=0) node_set.insert(dance_node[node]->up->id);
                if(dance_node[node]->down->val !=0) node_set.insert(dance_node[node]->down->id);
            }
        }
        cout << "Case #" << casen << ": " << ans << '\n';
    }
    return 0;
}