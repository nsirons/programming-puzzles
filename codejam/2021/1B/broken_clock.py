import os
import sys
from atexit import register
from io import BytesIO
sys.stdin = BytesIO(os.read(0, os.fstat(0).st_size))
sys.stdout = BytesIO()
register(lambda: os.write(1, sys.stdout.getvalue()))
input = lambda: sys.stdin.readline().rstrip('\r\n')
raw_input = lambda: sys.stdin.readline().rstrip('\r\n')


def sol(a,b,c):
    for h in xrange(0,12):
        for m in xrange(0,60):
            for s in xrange(0,60):
                for combo in [(a,b,c), (a,c,b), (b,a,c), (b,c,a), (c,a,b), (c,b,a)]:
                    aa,bb,cc = combo
                    nn =  (-aa + bb + 1000000000 *(3600*h - 11* (60*m + s)))
                    nnn = (-aa + cc + 1000000000 *(3600*h + 60*m - 719* s))
                    nnnn = (-bb + cc + 12000000000* (60* m - 59 *s))
                    lst = []
                    if nn%11==0:
                        lst.append(nn/11)
                    if nnn%719==0:
                        lst.append(nnn/719)
                    if nnnn%708==0:
                        lst.append(nnnn/708)
                    
                    for n in lst:
                        n = n %(360*12*10**10) % (10**9)
                        if abs(n) > 10**9-1:
                            n = 0
                        n = max(0,int(n))

                        h_deg = ((aa+n) % (360*12*10**10))
                        m_deg = ((bb+n) % (360*12*10**10))
                        s_deg = ((cc+n) % (360*12*10**10))
                        
                        h_must_be = (12*(h *30*10**10 + m * 5*10**9 + s*5*10**9/60.) + n ) % (360*12*10**10)
                        m_must_be = (12*(m * 6*10**10 + s    *10**9) + 12*n ) % (360*12*10**10)
                        s_must_be = (12*(s * 6*10**10) + 720*n ) % (360*12*10**10)
                        
                        rot = h_deg - h_must_be
                        m_deg -= rot
                        s_deg -= rot
                        m_deg = m_deg % (360*12*10**10)
                        s_deg = s_deg % (360*12*10**10)
                        
                        if  s_deg == s_must_be and m_deg == m_must_be:
                            return h,m,s,n
    assert False,(a,b,c)

for casen in xrange(int(input())):
    a,b,c = (int(x) for x in input().split())
    h,m,s,n = sol(a,b,c)
    print("Case #{}: {} {} {} {}".format(casen+1,h,m,s,n))
