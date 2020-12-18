input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    readlines(file)
end

push!(data, "")  # add empty line

function solve1()
    ans = 0
    mem = Dict([
        ("byr", false), ("iyr", false), ("eyr", false), ("hgt", false),
        ("hcl", false), ("ecl", false), ("pid", false), ("cid", false)
    ])
    for line in data
        arr = split(line, " ")
        if length(arr[1]) == 0
            ok = true
            for (key,value) in mem
                if !(value || key == "cid")
                    ok = false
                end 
                mem[key] = false
            end
            ans += ok
            continue
        else
            for code in arr
                code_spl = split(code, ":")
                mem[code_spl[1]] = true;
            end
        end
    end
    return ans
end


function solve2()
    ans = 0
    store = Dict([
        ("byr", false), ("iyr", false), ("eyr", false), ("hgt", false),
        ("hcl", false), ("ecl", false), ("pid", false), ("cid", false)
    ])
    for line in data
        arr = split(line, " ")
        if length(arr[1]) == 0
            ok = true
            for (key,value) in store
                if !((value) || (key == "cid"))
                    ok = false
                end 
                store[key] = false
            end
            ans += ok
            continue
        else
            for code in arr
                ok = false
                code_spl = split(code, ":")
                key = code_spl[1]
                val = code_spl[2]
                if key == "byr"
                    val = parse(Int, val)
                    if (val >= 1920) && (val <= 2002)
                        ok = true
                    end
                elseif key == "iyr"
                    val = parse(Int, val)
                    if (val >= 2010) && (val <= 2020)
                        ok = true
                    end
                elseif key == "eyr"
                    val = parse(Int, val)
                    if (val >= 2020) && (val <= 2030)
                        ok = true
                    end
                elseif key == "hgt"
                    if length(val) < 2
                        continue
                    end
                    if val[end-1:end] == "cm"
                        val = parse(Int, val[1:end-2])
                        if (val >= 150) && (val <= 193)
                            ok = true
                        end
                    elseif val[end-1:end] == "in"
                        val = parse(Int, val[1:end-2])
                        if (val >= 59) && (val <= 76)
                            ok = true
                        end
                    end
                elseif key == "hcl"
                    if (val[1] == '#') && (length(val) == 7)
                        ok = true
                        for (i,c) in enumerate(val)
                            if (i==1)
                                continue
                            end
                            if !(((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'f')))
                                ok = false
                                break
                            end
                        end
                    end
                elseif key == "ecl"
                    for smth in ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]
                        if smth == val
                            ok = true
                        end
                    end
                elseif key == "pid"
                    if (length(val) == 9) && (tryparse(Int, val) !== nothing)
                        ok = true
                    end
                else
                    # else cid, do nothing
                end
                store[key] = ok;
            end
        end
    end
    return ans
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())