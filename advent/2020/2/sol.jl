input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    read(file, String)
end

it = eachmatch(r"(\d+)-(\d+) ([a-z]): (\w+)", data)

function solve1() 
    ans = 0
    for x in it
        lower = parse(Int, x[1])
        upper = parse(Int, x[2])
        n = count(i->(i==first(x[3])), x[4])
        if (n >= lower) && (n <= upper)
            ans += 1
        end
    end
    return ans
end

function solve2()
    ans = 0
    for x in it
        lower = parse(Int, x[1])
        upper = parse(Int, x[2])
        if xor(x[4][lower] == first(x[3]), x[4][upper]==first(x[3]))
            ans += 1
        end
    end
    return ans
end


println("part 1: ", @time solve1())
println("part 2: ", @time solve2())