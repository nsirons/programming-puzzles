input = raw_input

for casen in xrange(int(input())):
    n = int(input())
    s = input()
    ans = []
    cnt = 1
    for i in xrange(n-1,0,-1):
        if s[i] > s[i-1]:
            cnt += 1
        else:
            ans.extend(list(xrange(cnt,0,-1)))
            cnt =1
    if cnt:
        ans.extend(list(xrange(cnt,0,-1)))
    ans = ' '.join((str(x) for x in ans[::-1]))
    print("Case #{}: {}".format(casen+1, ans))
