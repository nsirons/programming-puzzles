def sol(n, lst):
    ans = 0
    for i in range(n-1):
        d = lst[i+1]
        if lst[i] >= d:
            n1 = str(lst[i])
            n2 = str(lst[i+1])
            cnt = 0
            
            over=False
            under=False
            for j in range(len(n2)):
                if n1[j] == n2[j]:
                    cnt += 1
                elif n1[j] < n2[j]:
                    over=True
                    break
                else:
                    under=True
                    break
            
            if over:
                d = n2 + '0'*(len(n1)-len(n2))
            elif under:
                d = n2 + '0'*(len(n1)-len(n2)+1)
            else:
                d = str(lst[i] + 1)
                cnt1 = 0
                for j in range(len(n2)):
                    if d[j] == n2[j]:
                        cnt1 += 1
                    else:
                        break
                if cnt1 != cnt:
                    d = n2 + '0'*((len(n1)-len(n2)) + 1)
            ans += len(d)-len(n2)
        lst[i+1] = int(d)

    return ans


for casen in range(int(input())):
    n = int(input())
    lst = [int(x) for x in input().split()]
    print(f"Case #{casen+1}: {sol(n, lst)}")
