input = raw_input

def isPrime(n):
    if (n <= 1):
        return False
    if (n <= 3):
        return True
    if (n % 2 == 0 or n % 3 == 0):
        return False
    i = 5
    while(i * i <= n) :
        if (n % i == 0 or n % (i + 2) == 0) :
            return False
        i = i + 6
    return True

def sol1(n):
    if n == 6:
        return 6,0,0
    start = int(n**0.5)
    if start % 2== 0:
        start += 1
    p0 = start
    while not isPrime(p0):
        p0 += 2
    p1 = start
    while not isPrime(p1) and p1 > 0:
        p1 -= 2
    
    if p0 == p1:
        p1 -= 2
        while not isPrime(p1) and p1 > 0:
            p1 -= 2
    
    if p0 * p1 <= n:
        while p0 * p1 <= n:
            opt1 = p0*p1,p0,p1
            p1 = p0
            p0 += 2
            while not isPrime(p0):
                p0 += 2
    else:
        while p0 * p1 > n:
            p0 = p1
            p1 -= 2
            while not isPrime(p1) and p1 > 0:
                p1 -= 2
        opt1 = p0*p1,p0,p1
    return opt1


for casen in range(int(input())):
    z = int(input())
    print("Case #{}: {}".format(casen+1, sol1(z)[0]))