import os
import sys
from atexit import register
from io import BytesIO
sys.stdin = BytesIO(os.read(0, os.fstat(0).st_size))
sys.stdout = BytesIO()
register(lambda: os.write(1, sys.stdout.getvalue()))
input = lambda: sys.stdin.readline().rstrip('\r\n')
raw_input = lambda: sys.stdin.readline().rstrip('\r\n')


# Observations:
# if 
#   v[i+1] - v[i] > 1 -> impossible
#   v[i+1] - v[i] == 1 -> p[i+1] is smaller than p[i]
#   v[i+1] - v[i] <= 0 -> p[i+1] is bigger than  |v[i+1] - v[i]| + 1 previous pankakes
# v[i] == 1 next biggest pankake
# last v[i] == 1, at position i is the location of the biggest pankake

m = 1000000007



for casen in xrange(int(input())):
    n = int(input())
    v = [int(x) for x in input().split()]
    
    def rec(lst,bl):
        
        if len(lst) > 1:
            i = len(lst)-1
            if len(lst) -1 == bl:
                if lst[-1] == n-1:
                    pass
                else:
                    return 0
            if v[i] > v[i-1] and v[i] -v[i-1] == 1:
                if lst[i] < lst[i-1]:
                    pass
                else:
                    return 0
            else:
                cnt = 1
                curmax = lst[-1]
                for x in xrange(len(lst)-1,0,-1):
                    if curmax < lst[x]:
                        cnt += 1
                        if cnt > v[i]:
                            return 0
                    if v[x-1] == 1:
                        if lst[x-1] > lst[-1]: 
                            cnt += 1
                        break
                    curmax = max(curmax, lst[x])
                if cnt != v[i]:
                    return 0
        
        if len(lst) == n:
            return 1
        
        ans = 0
        for x in xrange(n):
            if x not in lst:
                lst.append(x)
                ans += rec(lst, bl)
                lst.pop()
        return ans
    
    big_loc = 0
    for i in xrange(n):
        if v[i] == 1:
            big_loc = i
    
    ans = rec([], big_loc)
    print("Case #{}: {}".format(casen+1, ans%m))
