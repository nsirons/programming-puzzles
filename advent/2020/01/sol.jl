using DelimitedFiles

input_path = joinpath(@__DIR__, "test.in")
data = readdlm(input_path, '\n', Int, '\n')


function solve1()
    n = size(data)[1]
    for i = 1:n-1
        for j = i+1:n
            if (data[i] + data[j] == 2020)
                return data[i] * data[j]
            end
        end
    end  
end

function solve2()
    n = size(data)[1]
    for i = 1:n-2
        for j = i+1:n-1
            for k = j+1:n
                if (data[i] + data[j] + data[k] == 2020)
                    return data[i]*data[j]*data[k]
                end
            end
        end
    end
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())