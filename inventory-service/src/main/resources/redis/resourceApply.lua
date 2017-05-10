local TIME_SUFFIX = ":time"
local MAX_TIME = 1000
local ApplyStatus =  {
    INIT = "INIT",
    PROCESSING = "PROCESSING"
  }

local form = redis.call("lindex", KEYS[1], -1)
if form then
  form = cjson.decode(form);  
  if form.status == ApplyStatus.INIT then
    return redis.call("rpop", KEYS[1])
  elseif form.status == ApplyStatus.PROCESSING then 
    redis.call("incr", KEYS[1] .. TIME_SUFFIX)
    local times = redis.call("get", KEYS[1] .. TIME_SUFFIX)
    if times and times - MAX_TIME > 0 then  --超过最大次数，返回异常
       redis.call("del", KEYS[1] .. TIME_SUFFIX)   
       return redis.call("rpop", KEYS[1])
    end
  end
end
return nil 