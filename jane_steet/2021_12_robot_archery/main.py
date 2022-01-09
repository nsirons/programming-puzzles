import copy
from collections import deque

import numpy as np

"""
I have divided the problem into two parts:
    1) How to calculate the probability of success for each next shot
    2) How to compute the total probability of D (Darin) winning

Part 1: It could be noticed that you have a higher probability of getting the arrow closer to the edge of the target 
rather than its centre. If we want to compute the probability for a small ring region it would be equal to ring_S / 
total_S. The area of the ring is equal to pi * (r_o^2 - r_i^2), lets write r_o = r_i + dr, where dr is the small 
width of the ring. Then it would become pi * (2*ri*dr + dr^2), lets ignore dr^2 as it would be too small Then 
probability for one region would be 2*ri*dr / R^2, then let's assume that R=1.0 (could be any number) Then 2*ri*dr is 
a probability of point landing on the ring, so that is our probability density function (pdf) We can check it as well 
by integrating it from 0 to 1 (R), we would have a result equal to 1.0, which means that we have included all 
possible cases where arrow could have landed on the target From pdf we can get cumulative distribution function (
cdf), simply by integrating it, so we would get cdf=r^2. It is quite useful because now we can answer the following 
question: "what is the probability of robots getting this shot or better?" It is important to remember, 
that all shots in the competition have the same distribution, no matter the robot and round number. Let's imagine 
robots A and B did their first shots r1 and r2 distance away. What is the probability of B getting at r2? well it is 
pdf(r2) = 2 * r2 What is the probability of B getting shot r2 < r1, it is 1-cdf(r2), and in case of the loss that 
probability would be cdf(r2) So the probability that robot B will continue playing is pdf(r2) * (1-cdf(r2)) Well, 
we can have many options for r2, all from 0 to R. So to calculate a total probability you would need to integrate 
this function pdf(r) * (1-cdf(r)) dr from 0 to 1. Which for the second shot would result in a probability 1/2. Now we 
can do a similar case where we just put more stuff into integral (either cdf(r) or (1-cdf(r), based on a pass and 
fail) and get probabilities for each next turn. It could be noticed that those probabilities have a pattern, 
and thus it is not necessary to perform integration every single step, but simply enough to know the probability of 
the previous step and step number. 
Pattern: Pnow - current probability of being and this step 
Ppass = Pnow * 1 / step 
Pfail = Pnow * (1-1/step) 

Part2:
This game is a tree, where at each child player X either stays or leaves. The game could be played for a long time (infinite)
 however the probability of such scenarios is way below 10 digits accuracy thus those cases could be pruned. 
 So just simulate the game and add the computed probability of someone winning.

"""


def dfs(q, prob_total, round_n):
    ans = np.zeros(4)

    # if prob is too small then ignore that case, it would not have an effect on ten decimal places estimation
    if prob_total < 1e-15:
        return ans
    # only 1 robot left -> winner
    if len(q) == 1:
        idx = q.popleft()
        ans[idx] += prob_total
        return ans

    next_player = q.popleft()
    q_pass = copy.copy(q)
    q_pass.append(next_player)
    p_next_round = 1./round_n
    ans += dfs(q_pass, prob_total * p_next_round, round_n + 1)

    q_fail = copy.copy(q)
    ans += dfs(q_fail, prob_total * (1-p_next_round), round_n+1)
    return ans


if __name__ == '__main__':
    q = deque(range(4))
    prob_lst = dfs(q, 1.0, 1)
    for i in range(4):
        print("{} : {:.16f} ".format(chr(65+i), prob_lst[i]))
    print("Total P approx {}".format(sum(prob_lst)))
