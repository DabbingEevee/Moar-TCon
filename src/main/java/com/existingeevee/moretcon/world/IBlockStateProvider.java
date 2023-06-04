package com.existingeevee.moretcon.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;

public interface IBlockStateProvider {

	IBlockState getNextBlock(Random rand);

	public class ConstantBlockStateProvider implements IBlockStateProvider {

		IBlockState state;

		public ConstantBlockStateProvider(IBlockState state) {
			this.state = state;
		}

		@Override
		public IBlockState getNextBlock(Random rand) {
			return state;
		}

	}

	public class RandomBlockStateProvider implements IBlockStateProvider {

		boolean trueIfProviders = false;

		IBlockState[] states;
		IBlockStateProvider[] providers;

		public RandomBlockStateProvider(IBlockState...states) {
			this.states = states;
		}

		public RandomBlockStateProvider(IBlockStateProvider...providers) {
			this.providers = providers;
			this.trueIfProviders = true;
		}

		@Override
		public IBlockState getNextBlock(Random rand) {
			if (trueIfProviders) {
				return providers[rand.nextInt(providers.length)].getNextBlock(rand);
			}
			return states[rand.nextInt(states.length)];
		}

	}

	public class AmountBlockStateProvider implements IBlockStateProvider {

		IBlockStateProvider rare;
		int raresLeft;
		IBlockStateProvider leftover;

		public AmountBlockStateProvider(IBlockStateProvider rare, IBlockStateProvider leftover, int amount) {
			this.rare = rare;
			this.leftover = leftover;
			this.raresLeft = amount;
		}

		@Override
		public IBlockState getNextBlock(Random rand) {
			if (raresLeft > 0) {
				raresLeft--;
				//.log(Integer.toString(raresLeft));
				return rare.getNextBlock(rand);
			}
			//Logging.log(Integer.toString(raresLeft) + "ouf");
			return leftover.getNextBlock(rand);
		}
	}
}
