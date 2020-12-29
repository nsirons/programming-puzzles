import sys
input = sys.stdin.readline


def bin_search(s, a, d):
    l = 0
    r = s+1
    dn = 0
    diff = float('inf')
    ret_val = 0
    while l <= r:
        mid = l + (r-l) // 2
        val = mid * (2*a+(mid-1)*d) // 2
        if val <= s and s - val < diff:
            dn = mid
            diff = s - val
            ret_val = val
            if diff == 0:
                break
        if val < s:
            l = mid + 1
        else:
            r = mid - 1
    return dn, ret_val


def solve(l, r):
    n = 0
    while abs(l-r) > n:  # before alternating
        s = abs(l-r)
        dn, ret_val = bin_search(s, n+1, 1)

        n += dn
        if l>r:
            l -= ret_val
        else:
            r -= ret_val

    if l >= r:
        dn1, ret_val1 = bin_search(l, n+1, 2)
        dn2, ret_val2 = bin_search(r, n+2, 2)
        r -= ret_val2
        l -= ret_val1
    else:
        dn1, ret_val1 = bin_search(r, n+1, 2)
        dn2, ret_val2 = bin_search(l, n+2, 2)
        r -= ret_val1
        l -= ret_val2

    n += dn1 + dn2
    return n,l,r


for t in range(1, int(input())+1):
    l, r = (int(x) for x in input().split())
    n, l, r = solve(l,r)
    print("Case #{}: {} {} {}".format(t, n, l, r))