input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    data = readlines(file)
end

function solve1(dx,dy)
    i = 1
    j = 0
    ans = 0
    ny = size(data)[1]
    nx = length(data[1])
    while i <= ny
        if (data[i][(j%nx)+1] == '#')
            ans += 1
        end
        i += dy
        j += dx
    end
    return ans
end

function solve2()
    ans = solve1(1,1)
    ans *= solve1(3,1)
    ans *= solve1(5,1)
    ans *= solve1(7,1)
    ans *= solve1(1,2)
    return ans    
end


println("part 1: ", @time solve1(3,1))
println("part 2: ", @time solve2())