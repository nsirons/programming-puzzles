t,n = (int(x) for x in input().split())

for _ in range(t):
    for i in range(n-1):
        print('M {} {}'.format(i+1,n))
        loc = int(input())
        if loc != i+1:
            print('S {} {}'.format(i+1,loc))
            input()
    print('D')
    input()