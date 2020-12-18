input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    readlines(file)
end

function solve1()
    ans = 0
    for line in data
        line = replace(line, 'B' => '1')
        line = replace(line, 'F' => '0')
        line = replace(line, 'R' => '1')
        line = replace(line, 'L' => '0')
        str1 = line[1:7]
        str2 = line[8:end]
        row = parse(Int, str1, base=2)
        col = parse(Int, str2, base=2) 
        ans = max(row*8+col, ans)
    end
    return ans
end


function solve2()
    ans = 0
    d = Dict([])
    for i = 1:126
        for j = 0:7
            d[i*8+j] = false 
        end
    end
    for line in data
        line = replace(line, 'B' => '1')
        line = replace(line, 'F' => '0')
        line = replace(line, 'R' => '1')
        line = replace(line, 'L' => '0')
        str1 = line[1:7]
        str2 = line[8:end]
        row = parse(Int, str1, base=2)
        col = parse(Int, str2, base=2) 
        id = row*8+col
        d[id] = true
    end
    for (key,val) in d
        if !val
            if haskey(d, key-1) && haskey(d,key+1)
                if d[key-1] && d[key+1]
                    return key
                end
            end
        end
    end
    return -1
end


println("part 1: ", @time solve1())
println("part 2: ", @time solve2())