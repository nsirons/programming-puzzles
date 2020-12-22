input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    data = readlines(file)
end

struct NumberPart1
    val::Int
end

Base.:+(a::NumberPart1, b::NumberPart1) = NumberPart1(a.val + b.val)
Base.:-(a::NumberPart1, b::NumberPart1) = NumberPart1(a.val * b.val)

struct NumberPart2
    val::Int
end

Base.:/(a::NumberPart2, b::NumberPart2) = NumberPart2(a.val + b.val)
Base.:-(a::NumberPart2, b::NumberPart2) = NumberPart2(a.val * b.val)

function solve1()
    ans = 0
    for line in data
        expr_res::NumberPart1 = eval(Meta.parse(replace(replace(line, r"(\d+)" => s"NumberPart1(\1)"), '*'=>'-')))
        ans += expr_res.val
    end
    return ans
end

function solve2()
    ans = 0
    for line in data
        expr_res::NumberPart2 = eval(Meta.parse(replace(replace(replace(line, r"(\d+)" => s"NumberPart2(\1)"), '+'=>'/'), '*'=>'-')))
        ans += expr_res.val
    end
    return ans 
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())