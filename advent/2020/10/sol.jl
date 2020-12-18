using DelimitedFiles

input_path = joinpath(@__DIR__, "test.in")
data = sort(readdlm(input_path, '\n', Int, '\n'), dims=1)

function solve1()
    diff1 = 0
    diff3 = 1
    cur = 0
    for val in data
        if (val - cur) == 1
            diff1 += 1
        elseif (val - cur) == 3
            diff3 += 1
        end
        cur = val
    end
    return diff1 * diff3
end


function solve2()
    dp = zeros(Int, data[end]+3)
    dp[0+1] = 1
    for val in data
        for i in 1:3
            if val+1-i > 0
                dp[val+1] += dp[val+1-i]
            end
        end
    end
    return dp[end-2]
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())