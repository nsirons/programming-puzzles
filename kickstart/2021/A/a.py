for casen in range(int(input())):
    n,k = (int(x) for x in input().split())
    s = input()
    goodness = 0
    for i in range(n//2):
        if s[i] != s[n-i-1]:
            goodness += 1
    print(f"Case #{casen+1}: {abs(goodness-k)}")