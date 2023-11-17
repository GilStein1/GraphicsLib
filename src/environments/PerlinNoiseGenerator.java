package environments;

import java.util.Random;

class PerlinNoiseGenerator {
    private static final int PERSISTENCE = 4;
    private static final int OCTAVES = 8;

    private int[] p = new int[512];
    private double[] g1 = new double[512];
    private double[][] g2 = new double[512][2];
    private double[][] g3 = new double[512][3];

    PerlinNoiseGenerator(long seed) {
        Random random = new Random(seed);

        for (int i = 0; i < 256; i++) {
            p[i] = i;
            g1[i] = random.nextDouble() * 2 - 1;

            for (int j = 0; j < 2; j++) {
                g2[i][j] = random.nextDouble() * 2 - 1;
            }
            normalize2(g2[i]);

            for (int j = 0; j < 3; j++) {
                g3[i][j] = random.nextDouble() * 2 - 1;
            }
            normalize3(g3[i]);
        }

        for (int i = 0; i < 256; i++) {
            int j = random.nextInt(256 - i) + i;
            int t = p[i];
            p[i] = p[j];
            p[j] = t;

            // Extend the arrays with the same values
            p[i + 256] = p[i];
            g1[i + 256] = g1[i];
            g2[i + 256][0] = g2[i][0];
            g2[i + 256][1] = g2[i][1];
            g3[i + 256][0] = g3[i][0];
            g3[i + 256][1] = g3[i][1];
            g3[i + 256][2] = g3[i][2];
        }
    }

    // Rest of the code remains the same



    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private double grad(int hash, double x) {
        return (hash & 1) == 0 ? x : -x;
    }

    private double grad(int hash, double x, double y) {
        int h = hash & 3;
        double u = h < 2 ? x : y;
        double v = h < 2 ? y : x;
        return ((h & 2) == 0 ? u : -u) + ((h & 1) == 0 ? v : -v);
    }

    private double grad(int hash, double x, double y, double z) {
        int h = hash & 15;
        double u = h < 8 ? x : y;
        double v = h < 4 ? y : h == 12 || h == 14 ? x : z;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    private double noise1(double x) {
        int ix0 = (int) Math.floor(x);
        double fx0 = x - ix0;
        double fx1 = fx0 - 1;
        double wx = fade(fx0);

        int ix = ix0 & 255;
        int ix1 = (ix0 + 1) & 255;

        double n0 = grad(p[ix], fx0);
        double n1 = grad(p[ix1], fx1);
        double noiseValue = lerp(wx, n0, n1);

        return noiseValue;
    }

    private double noise2(double x, double y) {
        int ix0 = (int) Math.floor(x);
        int iy0 = (int) Math.floor(y);
        double fx0 = x - ix0;
        double fy0 = y - iy0;
        double fx1 = fx0 - 1;
        double fy1 = fy0 - 1;
        double wx = fade(fx0);
        double wy = fade(fy0);

        int ix = ix0 & 255;
        int iy = iy0 & 255;
        int ix1 = (ix0 + 1) & 255;
        int iy1 = (iy0 + 1) & 255;

        double n0 = grad(p[ix + p[iy]], fx0, fy0);
        double n1 = grad(p[ix + p[iy1]], fx0, fy1);
        double n2 = grad(p[ix1 + p[iy]], fx1, fy0);
        double n3 = grad(p[ix1 + p[iy1]], fx1, fy1);
        double noiseValue = lerp(wy, lerp(wx, n0, n2), lerp(wx, n1, n3));

        return noiseValue;
    }

    private double noise3(double x, double y, double z) {
        int ix0 = (int) Math.floor(x);
        int iy0 = (int) Math.floor(y);
        int iz0 = (int) Math.floor(z);
        double fx0 = x - ix0;
        double fy0 = y - iy0;
        double fz0 = z - iz0;
        double fx1 = fx0 - 1;
        double fy1 = fy0 - 1;
        double fz1 = fz0 - 1;
        double wx = fade(fx0);
        double wy = fade(fy0);
        double wz = fade(fz0);

        int ix = ix0 & 255;
        int iy = iy0 & 255;
        int iz = iz0 & 255;
        int ix1 = (ix0 + 1) & 255;
        int iy1 = (iy0 + 1) & 255;
        int iz1 = (iz0 + 1) & 255;

        double n0 = grad(p[ix + p[iy + p[iz]]], fx0, fy0, fz0);
        double n1 = grad(p[ix + p[iy + p[iz1]]], fx0, fy0, fz1);
        double n2 = grad(p[ix + p[iy1 + p[iz]]], fx0, fy1, fz0);
        double n3 = grad(p[ix + p[iy1 + p[iz1]]], fx0, fy1, fz1);
        double n4 = grad(p[ix1 + p[iy + p[iz]]], fx1, fy0, fz0);
        double n5 = grad(p[ix1 + p[iy + p[iz1]]], fx1, fy0, fz1);
        double n6 = grad(p[ix1 + p[iy1 + p[iz]]], fx1, fy1, fz0);
        double n7 = grad(p[ix1 + p[iy1 + p[iz1]]], fx1, fy1, fz1);

        double noiseValue = lerp(wz,
                lerp(wy, lerp(wx, n0, n4), lerp(wx, n1, n5)),
                lerp(wy, lerp(wx, n2, n6), lerp(wx, n3, n7)));

        return noiseValue;
    }

    public double generateNoise(double x, double y, double frequency) {
        double noise = 0;
        double amplitude = 1;
        double maxAmplitude = 0;

        for (int i = 0; i < OCTAVES; i++) {
            noise += noise2(x * frequency, y * frequency) * amplitude;
            frequency *= 2;
            maxAmplitude += amplitude;
            amplitude /= PERSISTENCE;
        }

        return noise / maxAmplitude;
    }

    private void normalize2(double[] v) {
        double s = Math.sqrt(v[0] * v[0] + v[1] * v[1]);
        v[0] /= s;
        v[1] /= s;
    }

    private void normalize3(double[] v) {
        double s = Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
        v[0] /= s;
        v[1] /= s;
        v[2] /= s;
    }
}