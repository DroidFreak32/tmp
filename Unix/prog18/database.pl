#!/usr/bin/perl
while(<>)
{
	chop;
	@field=split(/\|/);
	if(1..4)
	{
		$dept=$field[2];
		$name=$field[1];
		$salary=$field[5];
		($f_name, $l_name)=split(/ +/,$name);
		$name=$l_name . "," . $f_name;
		$total+=$salary;
		printf("%d\t%s\t%s\t%d\n",$.,$name,$dept,$salary);
	}
}
printf("%s\t%d\n","Total salary = ",$total);
