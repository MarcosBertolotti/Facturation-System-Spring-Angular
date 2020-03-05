import { ItemBill } from './item-bill';
import { Client } from './client';

export class Bill {

    id: number;
    description: string;
    observation: string;
    items: Array<ItemBill> = new Array();
    client: Client;
    total: number;
    createAt: string;

    calculateGrandTotal(): number {
        this.total = 0;

        this.items.forEach((item: ItemBill) => {
            this.total += item.calculateAmount();
        });
        return this.total;
    }
}
