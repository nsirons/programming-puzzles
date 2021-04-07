def ok(score, mid, lst,x):
    # print(score, mid, lst)
    val = score + mid*x
    votes_rest = 1.-mid
    for other_score in lst:
        if other_score >= val:
            continue
        else:
            new_mid = (val-other_score)/x
            if new_mid > votes_rest:
                return False
            votes_rest -= new_mid
    return True


def solve(score, lst, x):
    l = 0.
    r = 1.
    ans = 0
    eps = 1e-9
    while (r-l) >= eps:
        # print(l,r)
        mid = (l+r)/ 2.
        if ok(score, mid, lst,x):
            ans = max(ans, mid)
            l = mid
        else:
            r = mid
    return ans*100

for casen in range(int(input())):
    lst = [int(x) for x in input().split()]
    n = lst[0]
    lst = lst[1:]
    x = sum(lst)
    ans = [f"{solve(score, lst[:i] + lst[i+1:], x):.6f}" for i, score in enumerate(lst)]
    print(f"Case #{casen+1}: {' '.join(ans)}")
