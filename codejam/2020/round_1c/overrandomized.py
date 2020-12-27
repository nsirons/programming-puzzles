# PyPy2

import sys
input = sys.stdin.readline

for t in range(1, int(input())+1):
    _ = input()
    set_first = set()
    set_all = set()
    d_cnt = dict()
    for i in range(10**4):
        _,r = input().split()
        set_first.add(r[0])
        try:
            d_cnt[r[0]] += 1
        except:
            d_cnt[r[0]] = 1
        for c in r:
            set_all.add(c)
    ans = set_all.difference(set_first).pop()
    ansr = sorted(d_cnt.items(), key=lambda x: x[1], reverse=True)
    print("Case #{}: {}".format(t, ans + ''.join((key for key, val in ansr))))