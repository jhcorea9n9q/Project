package com.java.web.hadoop;

import org.apache.hadoop.io.Text;

public class ProjectBeans {
	
	String Title;
	String Year;
	int Budget;
	int Profit;
	String Genre;
	double Popularity;
	String Country;
	String SuccessLevel;
	
	boolean TitleYn = true;
	boolean YearYn = true;
	boolean BudgetYn = true;
	boolean ProfitYn = true;
	boolean GenreYn = true;
	boolean PopularityYn = true;
	boolean CountryYn = true;
	boolean SuccessLevelYn = true;
	
	public ProjectBeans(Text val) {
		String[] col = val.toString().split("\t");
		
		// 영화제목
		try {
			if (col[8] != null) {
				this.Title = col[8];
			}else {
				this.TitleYn = false;
			}
		} catch (Exception e) {
			this.TitleYn = false;
		}
		
		// 개봉년도
		try {
			if( col[14] != null && col[14].length()==10 ) {
				String [] dat = col[14].split("-");
				this.Year = dat[0];
			} else {
				this.YearYn = false;
			}
		} catch (Exception e) {
			this.YearYn = false;
		}
		
		// 예산
		try {
			if(!col[2].equals("0")) {
				this.Budget = Integer.parseInt(col[2]);
			}else {
				this.BudgetYn = false;
			}
		} catch (Exception e) {
			this.Budget = 0;
			this.BudgetYn = false;
		}
		
		// 수입
		try {
			if(!col[15].equals("0")) {
				this.Profit = Integer.parseInt(col[15]);
			}else {
				this.ProfitYn = false;
			}
		} catch (Exception e) {
			this.Profit = 0;
			this.ProfitYn = false;
		}

		
		// 장르
		try {
			if (col[3] != null) {
				this.Genre = col[3];
			}else {
				this.GenreYn = false;
			}
		} catch (Exception e) {
			this.GenreYn = false;
		}
		
		// 인기도
		try {
			if (!col[10].equals("0")) {
				this.Popularity = Double.parseDouble(col[10]);
			}else {
				this.PopularityYn =  false;
			}
		} catch (Exception e) {
			this.Popularity = 0;
			this.PopularityYn =  false;
		}
		
		// 제작 국가
		try {
			if (col[13] != null) {
				this.Country = col[13];
			}else {
				this.CountryYn = false;
			}
		} catch (Exception e) {
			this.CountryYn = false;
		}
		
		try {
			if( getBudget() != 0 && getProfit() != 0 ) {
				if( isBudgetYn() && isProfitYn() ) {
					if( isYearYn() && isTitleYn() ) {
						int Hv = getProfit() - getBudget();
						if(Hv >= 500000000) {
							this.SuccessLevel = "500000000$";
						}else if(Hv >= 100000000 && Hv < 500000000) {
							this.SuccessLevel = "100000000$~499999999$";
						}else if(Hv >= 50000000 && Hv < 100000000) {
							this.SuccessLevel = "50000000$~99999999$";
						}else if(Hv > 0 && Hv < 50000000) {
							this.SuccessLevel = "1$~49999999$";;
						}else if(Hv < 0) {
							this.SuccessLevel = "-$";
						}
					}
				}
			}
		} catch (Exception e) {
			this.SuccessLevelYn = false;
		}
		
		
	}

	public String getTitle() {
		return Title;
	}

	public String getYear() {
		return Year;
	}

	public int getBudget() {
		return Budget;
	}

	public int getProfit() {
		return Profit;
	}

	public String getGenre() {
		return Genre;
	}

	public double getPopularity() {
		return Popularity;
	}

	public String getCountry() {
		return Country;
	}

	public boolean isTitleYn() {
		return TitleYn;
	}

	public boolean isYearYn() {
		return YearYn;
	}

	public boolean isBudgetYn() {
		return BudgetYn;
	}

	public boolean isProfitYn() {
		return ProfitYn;
	}

	public boolean isGenreYn() {
		return GenreYn;
	}

	public boolean isPopularityYn() {
		return PopularityYn;
	}

	public boolean isCountryYn() {
		return CountryYn;
	}

	public String getSuccessLevel() {
		return SuccessLevel;
	}

	public boolean isSuccessLevelYn() {
		return SuccessLevelYn;
	}
	
}
