input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    data = []
    for line in readlines(file)
        line = replace(line, 'B' => '1')
        line = replace(line, 'F' => '0')
        line = replace(line, 'R' => '1')
        line = replace(line, 'L' => '0')
        str1 = line[1:7]
        str2 = line[8:end]
        row = parse(Int, str1, base=2)
        col = parse(Int, str2, base=2)
        push!(data, (row,col))
    end
    (data)
end

function solve1()
    return maximum([row*8+col for (row, col) in data])
end

function solve2()
    d = Dict{Int,Bool}()
    for i = 1:126
        for j = 0:7
            d[i*8+j] = false 
        end
    end
    for (row, col) in data
        d[row*8+col] = true
    end
    for (key,val) in d
        if !val && get(d, key-1,false) && get(d,key+1,false)
            return key
        end
    end
    @assert false
end


println("part 1: ", @time solve1())
println("part 2: ", @time solve2())