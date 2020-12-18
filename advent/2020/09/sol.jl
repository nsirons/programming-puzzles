using DelimitedFiles

input_path = joinpath(@__DIR__, "test.in")
data = readdlm(input_path, '\n', Int, '\n')

function solve1()
    for i in 26:size(data)[1]
        ok = false
        for j in i-25:i-2
            for k in j+1:i-1
                if data[k]+data[j] == data[i]
                    ok = true
                    break
                end
            end
            if ok
                break
            end
        end
        if !ok
            return data[i]
        end
    end
end

function solve2()
    num = solve1()
    for i in 1:size(data)[1]
        cur_sum = 0
        ok = false
        mina = num
        maxa = 0
        for j in i:size(data)[1]
            cur_sum += data[j]
            mina = min(mina, data[j])
            maxa = max(maxa, data[j])
            if cur_sum == num
                return mina + maxa
            elseif cur_sum > num
                break
            end
        end
    end
            

    
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())