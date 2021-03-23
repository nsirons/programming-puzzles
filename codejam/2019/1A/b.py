t, n, m = (int(x) for x in input().split())
for _ in range(t):
    total = None
    for p in (4,3,5,7,11,13,17):
        print(18*f' {p}')
        coeff = sum((int(x) for x in input().split()))
        if total is None:
            total = set(range(coeff, 10**6+1, p))
        else:
            sub = set(range(coeff, 10**6+1, p))
            total = total.intersection(sub)

    ans = min(total)
    print(ans)
    input()
