h = 0:0.01:1;

H = -h.*log(h)-(1-h).*log(1-h);

plot(h, H)
