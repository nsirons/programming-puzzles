import os
import sys
from atexit import register
from io import BytesIO
sys.stdin = BytesIO(os.read(0, os.fstat(0).st_size))
sys.stdout = BytesIO()
register(lambda: os.write(1, sys.stdout.getvalue()))
input = lambda: sys.stdin.readline().rstrip('\r\n')
raw_input = lambda: sys.stdin.readline().rstrip('\r\n')


def mix(ans1, beg):
    sol = True
    if ans1 == '':
        ans1 = beg
    else:
        for j in range(len(beg)):
            if j < len(ans1) and beg[j] != ans1[j]:
                sol = False
                break
            elif j >= len(ans1):
                ans1 += beg[j]
    return ans1, sol


for t in xrange(1, 1 + int(input())):
    n = int(input())
    ans1 = ''
    ans2 = ''
    ans3 = ''
    sol = True
    for i in xrange(n):
        inp = input()
        ind_s = inp.index('*')
        ind_e = len(inp) - inp[::-1].index('*')
        if ind_s == ind_e:
            m = ''
        else:
            m = inp[ind_s:ind_e].replace('*', '')
        beg = inp[:ind_s]
        end = inp[ind_e:]
        ans3 += m
        if sol:
            ans1, sol = mix(ans1, beg)
            if sol:
                ans2, sol = mix(ans2, end[::-1])
    if sol:
        ans = ans1 + ans3 + ans2[::-1]
    else:
        ans = '*'
    print("Case #{}: {}".format(t, ans))
