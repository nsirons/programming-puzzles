import os
import sys
from atexit import register
from io import BytesIO
sys.stdin = BytesIO(os.read(0, os.fstat(0).st_size))
sys.stdout = BytesIO()
register(lambda: os.write(1, sys.stdout.getvalue()))
input = lambda: sys.stdin.readline().rstrip('\r\n')
raw_input = lambda: sys.stdin.readline().rstrip('\r\n')



for casen in xrange(int(input())):
    n,a,b = (int(x) for x in input().split())
    lst = [int(x) for x in input().split()]
    ans = "IMPOSSIBLE"
    for h in xrange(len(lst),420):
        my_lst = [0 for _ in xrange(h+1)]
        my_lst[h] = 1
        for node in xrange(h,-1,-1):
            if (node<len(lst) and my_lst[node] < lst[node]):
                break
            if (node < len(lst) and my_lst[node] > lst[node]) or node >= len(lst):
                if node < len(lst):
                    how_many = my_lst[node] - lst[node]
                else:
                    how_many = my_lst[node]
                my_lst[node] -= how_many
                if (node-a) >=0:
                    my_lst[node-a] += how_many
                if (node-b) >=0:
                    my_lst[node-b] += how_many

        else:
            ans = h+1
            break
    
    print("Case #{}: {}".format(casen+1, ans))
