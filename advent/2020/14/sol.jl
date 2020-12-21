input_path = joinpath(@__DIR__, "test.in")
data = open(input_path) do file
    data = readlines(file)
end

function solve1()
    mem = Dict{Int, Int}()
    mask = ""
    for line in data
        if line[1:4] == "mask"
            mask = line[8:end]
        else
            m = match(r"mem\[(\d+)\] = (\d+)", line)
            ind = parse(Int, m[1])
            bin_val = string(parse(Int,m[2]), base=2, pad=36)
            bin_val = [mask[i] != 'X' ? mask[i] : bin_val[i] for i in 1:36]
            mem[ind] = parse(Int,join(bin_val, ""), base=2)
        end
    end
    return sum((v for (_, v) in mem))
end


function solve2()
    mem = Dict{Int, Int}()
    mask = ""
    for line in data
        if line[1:4] == "mask"
            mask = line[8:end]
        else
            m = match(r"mem\[(\d+)\] = (\d+)", line)
            val = parse(Int, m[2])
            bin_val = collect(string(parse(Int,m[1]), base=2, pad=36))
            cnt = 0
            for i in 1:36
                if mask[i] == '0'
                elseif mask[i] == '1'
                    bin_val[i] = '1'
                else
                    bin_val[i] = 'X'
                    cnt += 1
                end
            end
            x_ind = [k for (k,c) in enumerate(bin_val) if c == 'X']
            for j=0:2^cnt-1
                arr = Array(bin_val)
                bit_str = string(j, base=2, pad=cnt)
                for (p,k) in enumerate(x_ind)
                    arr[k] = bit_str[p]
                end
                ind = parse(Int,join(arr, ""), base=2)
                mem[ind] = val
            end
        end
    end
    return sum((v for (_,v) in mem))
end

println("part 1: ", @time solve1())
println("part 2: ", @time solve2())