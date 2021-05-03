import os
import sys
from atexit import register
from io import BytesIO
sys.stdin = BytesIO(os.read(0, os.fstat(0).st_size))
sys.stdout = BytesIO()
register(lambda: os.write(1, sys.stdout.getvalue()))
input = lambda: sys.stdin.readline().rstrip('\r\n')
raw_input = lambda: sys.stdin.readline().rstrip('\r\n')


def sol(n):
    nstr = str(n)
    ans = ''
    for i in range(1,100):
        ans += str(i)
        toaddnext = i+1
        if len(ans) >= len(nstr):
            break
    if int(ans) > n:
        ans = int(ans)
    else:
        ans += str(toaddnext)
    ans = int(ans) # 1234567.....
    
    if len(nstr) % 2 == 1:
        half = ((len(nstr)+1)//2) -1
        anss = '1' + '0'*half + '1' + '0' *(half-1) + '1'
        anss = int(anss)
        ans = min(ans, anss)  # 1011, 1001001, 10001001 ....
        
    for i in range(1,(len(nstr)+2)//2): # prefix length
        for dx in range(1000):  # <- not sure why
            lst = nstr[:i]
            if len(str(int(nstr[:i])+dx)) != len(lst):
                continue
            curval = int(nstr[:i])+dx
            lst = str(curval)
            lcur = i
            for j in range(len(nstr)):
                l = len(str(curval + 1))
                val = int(nstr[lcur:lcur+l])
                if val - curval == 1:
                    lst += nstr[lcur:lcur+l]
                    curval += 1
                else:
                    break
            while len(lst) < len(nstr):
                lst += str(curval+1)
                curval += 1
            cand = int(''.join(lst))
            if cand < n:
                lst += str(curval+1)
            cand = int(''.join(lst))
            ans = min(ans, cand)
    return ans


for casen in xrange(int(input())):
    n = int(input())+1
    print("Case #{}: {}".format(casen+1,sol(n)))
