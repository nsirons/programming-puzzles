def sol(lst):
    ans = 2
    n = len(lst)
    for i in range(n-1):
        ogval = lst[i]
        # for init prog
        if i+2<n:
            a = lst[i+2] - lst[i+1]
            cnt = 3
            for j in range(i+2,n-1):
                if lst[j+1] - lst[j] == a:
                    cnt += 1
                else:
                    break
            ans = max(ans, cnt)
        # update as midl value
        if i != 0:
            diff = lst[i+1] - lst[i-1]
            if diff % 2 == 0:
                cnt = 3
                a = diff // 2
                # go left
                for j in range(i-1,0,-1):
                    if lst[j] - lst[j-1] == a:
                        cnt += 1
                    else:
                        break
                # go right
                for j in range(i+1, n-1):
                    if lst[j+1] - lst[j] == a:
                        cnt += 1
                    else:
                        break
                ans = max(ans, cnt)
    return ans

def brutebrute(lst):
    n = len(lst)    
    return max(sol(lst), sol(lst[::-1]))

input = raw_input
for casen in range(int(input())):
    n = int(input())
    lst = [int(x) for x in input().split()]
    print("Case #{}: {}".format(casen+1, brutebrute(lst)))
