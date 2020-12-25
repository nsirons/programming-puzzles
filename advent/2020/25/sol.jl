input_path = joinpath(@__DIR__, "test.in")
(inp1, inp2) = open(input_path) do file
    inp1 = parse(Int, readline(file))
    inp2 = parse(Int, readline(file))
    (inp1, inp2)
end

function solve1(inp1, inp2)
    subj_number = 7
    val = 1
    ans = 1
    m = 20201227
    
    while true
        val *= subj_number
        val %= m
        ans *= inp2
        ans %= m
        if val == inp1
            return ans
        end
    end
end

println("part 1: ",@time solve1(inp1, inp2))